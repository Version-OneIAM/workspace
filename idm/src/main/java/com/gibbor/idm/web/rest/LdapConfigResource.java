package com.gibbor.idm.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gibbor.idm.domain.LdapConfig;
import com.gibbor.idm.service.LdapConfigService;
import com.gibbor.idm.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LdapConfig.
 */
@RestController
@RequestMapping("/api")
public class LdapConfigResource {

    private final Logger log = LoggerFactory.getLogger(LdapConfigResource.class);
        
    @Inject
    private LdapConfigService ldapConfigService;
    
    /**
     * POST  /ldapConfigs -> Create a new ldapConfig.
     */
    @RequestMapping(value = "/ldapConfigs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LdapConfig> createLdapConfig(@Valid @RequestBody LdapConfig ldapConfig) throws URISyntaxException {
        log.debug("REST request to save LdapConfig : {}", ldapConfig);
        if (ldapConfig.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ldapConfig", "idexists", "A new ldapConfig cannot already have an ID")).body(null);
        }
        LdapConfig result = ldapConfigService.save(ldapConfig);
        return ResponseEntity.created(new URI("/api/ldapConfigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ldapConfig", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ldapConfigs -> Updates an existing ldapConfig.
     */
    @RequestMapping(value = "/ldapConfigs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LdapConfig> updateLdapConfig(@Valid @RequestBody LdapConfig ldapConfig) throws URISyntaxException {
        log.debug("REST request to update LdapConfig : {}", ldapConfig);
        if (ldapConfig.getId() == null) {
            return createLdapConfig(ldapConfig);
        }
        LdapConfig result = ldapConfigService.save(ldapConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ldapConfig", ldapConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ldapConfigs -> get all the ldapConfigs.
     */
    @RequestMapping(value = "/ldapConfigs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LdapConfig> getAllLdapConfigs() {
        log.debug("REST request to get all LdapConfigs");
        return ldapConfigService.findAll();
            }

    /**
     * GET  /ldapConfigs/:id -> get the "id" ldapConfig.
     */
    @RequestMapping(value = "/ldapConfigs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LdapConfig> getLdapConfig(@PathVariable Long id) {
        log.debug("REST request to get LdapConfig : {}", id);
        LdapConfig ldapConfig = ldapConfigService.findOne(id);
        return Optional.ofNullable(ldapConfig)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ldapConfigs/:id -> delete the "id" ldapConfig.
     */
    @RequestMapping(value = "/ldapConfigs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLdapConfig(@PathVariable Long id) {
        log.debug("REST request to delete LdapConfig : {}", id);
        ldapConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ldapConfig", id.toString())).build();
    }
}
