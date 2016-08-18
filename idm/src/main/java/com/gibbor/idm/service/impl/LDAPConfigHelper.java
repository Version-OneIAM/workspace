package com.gibbor.idm.service.impl;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gibbor.idm.domain.LdapConfig;
import com.gibbor.idm.service.impl.DirContextGlobal;


public class LDAPConfigHelper {
	
	
	private final Logger log = LoggerFactory.getLogger(LDAPConfigHelper.class);
		
	@SuppressWarnings("null")
	public DirContextGlobal getLDAPContext(LdapConfig ldapConfig) throws NamingException {
		
		DirContextGlobal dirContextGlobal = null;
		InitialDirContext ctx = null;
		
		final String ldapAdServer = "ldap://"+ldapConfig.getIpaddress()+":"+ldapConfig.getPort();
		
		log.debug(" ldapConfig.getUserbasedn() " + ldapConfig.getUserbasedn());
        final String ldapSearchBase = ldapConfig.getUserbasedn();//"cn=users,dc=gibbor,dc=one";//ldapConfig.getUserbasedn();//"cn=users,dc=uxms01,dc=com";
        
        log.debug(" ldapConfig.getName() " + ldapConfig.getName());
        final String ldapUsername = ldapConfig.getName();//"administrator@gibbor.one";//ldapConfig.getName();//"administrator@uxms01.com";
        final String ldapPassword = "Gibbor@12";
        
        // final String ldapPassword = ldapConfig.getPassword;
        
        final String ldapAccountToLookup = "tone1";
        
        
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        if(ldapUsername != null) {
            env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
        }
        if(ldapPassword != null) {
            env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
        }
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);

        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");
        
        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);
        
        ctx = new InitialDirContext(env);
        LDAPConfigHelper ldap = new LDAPConfigHelper();
        
        //1) lookup the ldap account
        SearchResult srLdapUser = ldap.findAccountByAccountName(ctx, ldapSearchBase, ldapAccountToLookup);
        
        log.debug(" srLdapUser " + srLdapUser);
        
        if(srLdapUser != null){
        	
        	log.debug("UPN : ");
        	dirContextGlobal = new DirContextGlobal();
        	
        	dirContextGlobal.setDirContext(true);
        	
        }else {
        	log.debug("Exception in getting the LDAP context..." );
        	
        }
		
		return dirContextGlobal;
	}
	
	
	public SearchResult findAccountByAccountName(DirContext ctx, String ldapSearchBase, String accountName) throws NamingException {

        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + accountName + "))";
        
        log.debug(" input  for the accountName: " + accountName);

        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        log.debug("one");
        NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);
        log.debug("two");

        SearchResult searchResult = null;
        if(results.hasMoreElements()) {
             searchResult = (SearchResult) results.nextElement();

            //make sure there is not another item available, there should be only 1 match
            if(results.hasMoreElements()) {
            	log.debug("users for the accountName: " + accountName);
                //return null;
            }
        }
        log.debug("Begore leaving the  findAccountByAccountName");
        return searchResult;
    }

	
	

}
 