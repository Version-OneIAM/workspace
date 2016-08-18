package com.gibbor.idm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.gibbor.idm.domain.enumeration.PortTypesEnum;

import com.gibbor.idm.domain.enumeration.FetchStatesEnum;

/**
 * A LdapConfig.
 */
@Entity
@Table(name = "ldap_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LdapConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "domainname", nullable = false)
    private String domainname;
    
    @Column(name = "netbiosdomainname")
    private String netbiosdomainname;
    
    @Column(name = "whenmanaged")
    private LocalDate whenmanaged;
    
    @Column(name = "lastsynctime")
    private LocalDate lastsynctime;
    
    @Column(name = "isdefault")
    private Boolean isdefault;
    
    @Column(name = "isdeleted")
    private Boolean isdeleted;
    
    @Column(name = "domainsid")
    private String domainsid;
    
    @NotNull
    @Column(name = "ipaddress", nullable = false)
    private String ipaddress;
    
    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;
    
    @Column(name = "issecure")
    private Boolean issecure;
    
    @NotNull
    @Column(name = "userbasedn", nullable = false)
    private String userbasedn;
    
    @Column(name = "groupbasedn")
    private String groupbasedn;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "portype")
    private PortTypesEnum portype;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "fetchstatus")
    private FetchStatesEnum fetchstatus;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDomainname() {
        return domainname;
    }
    
    public void setDomainname(String domainname) {
        this.domainname = domainname;
    }

    public String getNetbiosdomainname() {
        return netbiosdomainname;
    }
    
    public void setNetbiosdomainname(String netbiosdomainname) {
        this.netbiosdomainname = netbiosdomainname;
    }

    public LocalDate getWhenmanaged() {
        return whenmanaged;
    }
    
    public void setWhenmanaged(LocalDate whenmanaged) {
        this.whenmanaged = whenmanaged;
    }

    public LocalDate getLastsynctime() {
        return lastsynctime;
    }
    
    public void setLastsynctime(LocalDate lastsynctime) {
        this.lastsynctime = lastsynctime;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }
    
    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Boolean getIsdeleted() {
        return isdeleted;
    }
    
    public void setIsdeleted(Boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public String getDomainsid() {
        return domainsid;
    }
    
    public void setDomainsid(String domainsid) {
        this.domainsid = domainsid;
    }

    public String getIpaddress() {
        return ipaddress;
    }
    
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Integer getPort() {
        return port;
    }
    
    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean getIssecure() {
        return issecure;
    }
    
    public void setIssecure(Boolean issecure) {
        this.issecure = issecure;
    }

    public String getUserbasedn() {
        return userbasedn;
    }
    
    public void setUserbasedn(String userbasedn) {
        this.userbasedn = userbasedn;
    }

    public String getGroupbasedn() {
        return groupbasedn;
    }
    
    public void setGroupbasedn(String groupbasedn) {
        this.groupbasedn = groupbasedn;
    }

    public PortTypesEnum getPortype() {
        return portype;
    }
    
    public void setPortype(PortTypesEnum portype) {
        this.portype = portype;
    }

    public FetchStatesEnum getFetchstatus() {
        return fetchstatus;
    }
    
    public void setFetchstatus(FetchStatesEnum fetchstatus) {
        this.fetchstatus = fetchstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LdapConfig ldapConfig = (LdapConfig) o;
        if(ldapConfig.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ldapConfig.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LdapConfig{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", domainname='" + domainname + "'" +
            ", netbiosdomainname='" + netbiosdomainname + "'" +
            ", whenmanaged='" + whenmanaged + "'" +
            ", lastsynctime='" + lastsynctime + "'" +
            ", isdefault='" + isdefault + "'" +
            ", isdeleted='" + isdeleted + "'" +
            ", domainsid='" + domainsid + "'" +
            ", ipaddress='" + ipaddress + "'" +
            ", port='" + port + "'" +
            ", issecure='" + issecure + "'" +
            ", userbasedn='" + userbasedn + "'" +
            ", groupbasedn='" + groupbasedn + "'" +
            ", portype='" + portype + "'" +
            ", fetchstatus='" + fetchstatus + "'" +
            '}';
    }
}
