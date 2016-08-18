package com.gibbor.idm.service.impl;

import com.gibbor.idm.service.LdapConfigService;
import com.gibbor.idm.domain.LdapConfig;
import com.gibbor.idm.repository.LdapConfigRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.naming.NamingException;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing LdapConfig.
 */
@Service
@Transactional
public class LdapConfigServiceImpl implements LdapConfigService{

    private final Logger log = LoggerFactory.getLogger(LdapConfigServiceImpl.class);
    
    @Inject
    private LdapConfigRepository ldapConfigRepository;
    
    /**
     * Save a ldapConfig.
     * @return the persisted entity
     */
    public LdapConfig save(LdapConfig ldapConfig){
        log.debug("Request to save LdapConfig : {}", ldapConfig);
        LdapConfig result= null;
        
        try{
        	LDAPConfigHelper ldapConfigHelper = new LDAPConfigHelper();
            DirContextGlobal dirContextGlobal = ldapConfigHelper.getLDAPContext(ldapConfig);
        	log.debug("####" + dirContextGlobal.isDirContext());
        	if(dirContextGlobal.isDirContext()){
        		result = ldapConfigRepository.save(ldapConfig);
        	}else{
        		log.debug("Exception in getting the LDAP context..." );
        	}
        }catch(Exception e){
        	log.debug("Exception in saving LDAP details ..." + e.getMessage());
        }	
        return result;
    }

    /**
     *  get all the ldapConfigs.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<LdapConfig> findAll() {
        log.debug("Request to get all LdapConfigs");
        List<LdapConfig> result = ldapConfigRepository.findAll();
        return result;
    }

    /**
     *  get one ldapConfig by id.
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public LdapConfig findOne(Long id) {
        log.debug("Request to get LdapConfig : {}", id);
        LdapConfig ldapConfig = ldapConfigRepository.findOne(id);
        return ldapConfig;
    }

    /**
     *  delete the  ldapConfig by id.
     */
    public void delete(Long id) {
        log.debug("Request to delete LdapConfig : {}", id);
        ldapConfigRepository.delete(id);
    }
}
