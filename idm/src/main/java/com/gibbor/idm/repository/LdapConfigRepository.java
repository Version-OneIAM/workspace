package com.gibbor.idm.repository;

import com.gibbor.idm.domain.LdapConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LdapConfig entity.
 */
public interface LdapConfigRepository extends JpaRepository<LdapConfig,Long> {

}
