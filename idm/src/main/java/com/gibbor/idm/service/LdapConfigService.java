package com.gibbor.idm.service;

import com.gibbor.idm.domain.LdapConfig;

import java.util.List;

/**
 * Service Interface for managing LdapConfig.
 */
public interface LdapConfigService {

    /**
     * Save a ldapConfig.
     * @return the persisted entity
     */
    public LdapConfig save(LdapConfig ldapConfig);

    /**
     *  get all the ldapConfigs.
     *  @return the list of entities
     */
    public List<LdapConfig> findAll();

    /**
     *  get the "id" ldapConfig.
     *  @return the entity
     */
    public LdapConfig findOne(Long id);

    /**
     *  delete the "id" ldapConfig.
     */
    public void delete(Long id);
}
