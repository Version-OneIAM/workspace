package com.gibbor.idm.web.rest;

import com.gibbor.idm.Application;
import com.gibbor.idm.domain.LdapConfig;
import com.gibbor.idm.repository.LdapConfigRepository;
import com.gibbor.idm.service.LdapConfigService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gibbor.idm.domain.enumeration.PortTypesEnum;
import com.gibbor.idm.domain.enumeration.FetchStatesEnum;

/**
 * Test class for the LdapConfigResource REST controller.
 *
 * @see LdapConfigResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LdapConfigResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DOMAINNAME = "AAAAA";
    private static final String UPDATED_DOMAINNAME = "BBBBB";
    private static final String DEFAULT_NETBIOSDOMAINNAME = "AAAAA";
    private static final String UPDATED_NETBIOSDOMAINNAME = "BBBBB";

    private static final LocalDate DEFAULT_WHENMANAGED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_WHENMANAGED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LASTSYNCTIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTSYNCTIME = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ISDEFAULT = false;
    private static final Boolean UPDATED_ISDEFAULT = true;

    private static final Boolean DEFAULT_ISDELETED = false;
    private static final Boolean UPDATED_ISDELETED = true;
    private static final String DEFAULT_DOMAINSID = "AAAAA";
    private static final String UPDATED_DOMAINSID = "BBBBB";
    private static final String DEFAULT_IPADDRESS = "AAAAA";
    private static final String UPDATED_IPADDRESS = "BBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final Boolean DEFAULT_ISSECURE = false;
    private static final Boolean UPDATED_ISSECURE = true;
    private static final String DEFAULT_USERBASEDN = "AAAAA";
    private static final String UPDATED_USERBASEDN = "BBBBB";
    private static final String DEFAULT_GROUPBASEDN = "AAAAA";
    private static final String UPDATED_GROUPBASEDN = "BBBBB";
    
    private static final PortTypesEnum DEFAULT_PORTYPE = PortTypesEnum.TCP;
    private static final PortTypesEnum UPDATED_PORTYPE = PortTypesEnum.UDP;
    
    private static final FetchStatesEnum DEFAULT_FETCHSTATUS = FetchStatesEnum.Unknown;
    private static final FetchStatesEnum UPDATED_FETCHSTATUS = FetchStatesEnum.FetchSuccessful;

    @Inject
    private LdapConfigRepository ldapConfigRepository;

    @Inject
    private LdapConfigService ldapConfigService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLdapConfigMockMvc;

    private LdapConfig ldapConfig;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LdapConfigResource ldapConfigResource = new LdapConfigResource();
        ReflectionTestUtils.setField(ldapConfigResource, "ldapConfigService", ldapConfigService);
        this.restLdapConfigMockMvc = MockMvcBuilders.standaloneSetup(ldapConfigResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ldapConfig = new LdapConfig();
        ldapConfig.setName(DEFAULT_NAME);
        ldapConfig.setDomainname(DEFAULT_DOMAINNAME);
        ldapConfig.setNetbiosdomainname(DEFAULT_NETBIOSDOMAINNAME);
        ldapConfig.setWhenmanaged(DEFAULT_WHENMANAGED);
        ldapConfig.setLastsynctime(DEFAULT_LASTSYNCTIME);
        ldapConfig.setIsdefault(DEFAULT_ISDEFAULT);
        ldapConfig.setIsdeleted(DEFAULT_ISDELETED);
        ldapConfig.setDomainsid(DEFAULT_DOMAINSID);
        ldapConfig.setIpaddress(DEFAULT_IPADDRESS);
        ldapConfig.setPort(DEFAULT_PORT);
        ldapConfig.setIssecure(DEFAULT_ISSECURE);
        ldapConfig.setUserbasedn(DEFAULT_USERBASEDN);
        ldapConfig.setGroupbasedn(DEFAULT_GROUPBASEDN);
        ldapConfig.setPortype(DEFAULT_PORTYPE);
        ldapConfig.setFetchstatus(DEFAULT_FETCHSTATUS);
    }

    @Test
    @Transactional
    public void createLdapConfig() throws Exception {
        int databaseSizeBeforeCreate = ldapConfigRepository.findAll().size();

        // Create the LdapConfig

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isCreated());

        // Validate the LdapConfig in the database
        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeCreate + 1);
        LdapConfig testLdapConfig = ldapConfigs.get(ldapConfigs.size() - 1);
        assertThat(testLdapConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLdapConfig.getDomainname()).isEqualTo(DEFAULT_DOMAINNAME);
        assertThat(testLdapConfig.getNetbiosdomainname()).isEqualTo(DEFAULT_NETBIOSDOMAINNAME);
        assertThat(testLdapConfig.getWhenmanaged()).isEqualTo(DEFAULT_WHENMANAGED);
        assertThat(testLdapConfig.getLastsynctime()).isEqualTo(DEFAULT_LASTSYNCTIME);
        assertThat(testLdapConfig.getIsdefault()).isEqualTo(DEFAULT_ISDEFAULT);
        assertThat(testLdapConfig.getIsdeleted()).isEqualTo(DEFAULT_ISDELETED);
        assertThat(testLdapConfig.getDomainsid()).isEqualTo(DEFAULT_DOMAINSID);
        assertThat(testLdapConfig.getIpaddress()).isEqualTo(DEFAULT_IPADDRESS);
        assertThat(testLdapConfig.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testLdapConfig.getIssecure()).isEqualTo(DEFAULT_ISSECURE);
        assertThat(testLdapConfig.getUserbasedn()).isEqualTo(DEFAULT_USERBASEDN);
        assertThat(testLdapConfig.getGroupbasedn()).isEqualTo(DEFAULT_GROUPBASEDN);
        assertThat(testLdapConfig.getPortype()).isEqualTo(DEFAULT_PORTYPE);
        assertThat(testLdapConfig.getFetchstatus()).isEqualTo(DEFAULT_FETCHSTATUS);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ldapConfigRepository.findAll().size();
        // set the field null
        ldapConfig.setName(null);

        // Create the LdapConfig, which fails.

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isBadRequest());

        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDomainnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ldapConfigRepository.findAll().size();
        // set the field null
        ldapConfig.setDomainname(null);

        // Create the LdapConfig, which fails.

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isBadRequest());

        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIpaddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = ldapConfigRepository.findAll().size();
        // set the field null
        ldapConfig.setIpaddress(null);

        // Create the LdapConfig, which fails.

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isBadRequest());

        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = ldapConfigRepository.findAll().size();
        // set the field null
        ldapConfig.setPort(null);

        // Create the LdapConfig, which fails.

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isBadRequest());

        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserbasednIsRequired() throws Exception {
        int databaseSizeBeforeTest = ldapConfigRepository.findAll().size();
        // set the field null
        ldapConfig.setUserbasedn(null);

        // Create the LdapConfig, which fails.

        restLdapConfigMockMvc.perform(post("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isBadRequest());

        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLdapConfigs() throws Exception {
        // Initialize the database
        ldapConfigRepository.saveAndFlush(ldapConfig);

        // Get all the ldapConfigs
        restLdapConfigMockMvc.perform(get("/api/ldapConfigs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ldapConfig.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].domainname").value(hasItem(DEFAULT_DOMAINNAME.toString())))
                .andExpect(jsonPath("$.[*].netbiosdomainname").value(hasItem(DEFAULT_NETBIOSDOMAINNAME.toString())))
                .andExpect(jsonPath("$.[*].whenmanaged").value(hasItem(DEFAULT_WHENMANAGED.toString())))
                .andExpect(jsonPath("$.[*].lastsynctime").value(hasItem(DEFAULT_LASTSYNCTIME.toString())))
                .andExpect(jsonPath("$.[*].isdefault").value(hasItem(DEFAULT_ISDEFAULT.booleanValue())))
                .andExpect(jsonPath("$.[*].isdeleted").value(hasItem(DEFAULT_ISDELETED.booleanValue())))
                .andExpect(jsonPath("$.[*].domainsid").value(hasItem(DEFAULT_DOMAINSID.toString())))
                .andExpect(jsonPath("$.[*].ipaddress").value(hasItem(DEFAULT_IPADDRESS.toString())))
                .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
                .andExpect(jsonPath("$.[*].issecure").value(hasItem(DEFAULT_ISSECURE.booleanValue())))
                .andExpect(jsonPath("$.[*].userbasedn").value(hasItem(DEFAULT_USERBASEDN.toString())))
                .andExpect(jsonPath("$.[*].groupbasedn").value(hasItem(DEFAULT_GROUPBASEDN.toString())))
                .andExpect(jsonPath("$.[*].portype").value(hasItem(DEFAULT_PORTYPE.toString())))
                .andExpect(jsonPath("$.[*].fetchstatus").value(hasItem(DEFAULT_FETCHSTATUS.toString())));
    }

    @Test
    @Transactional
    public void getLdapConfig() throws Exception {
        // Initialize the database
        ldapConfigRepository.saveAndFlush(ldapConfig);

        // Get the ldapConfig
        restLdapConfigMockMvc.perform(get("/api/ldapConfigs/{id}", ldapConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ldapConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.domainname").value(DEFAULT_DOMAINNAME.toString()))
            .andExpect(jsonPath("$.netbiosdomainname").value(DEFAULT_NETBIOSDOMAINNAME.toString()))
            .andExpect(jsonPath("$.whenmanaged").value(DEFAULT_WHENMANAGED.toString()))
            .andExpect(jsonPath("$.lastsynctime").value(DEFAULT_LASTSYNCTIME.toString()))
            .andExpect(jsonPath("$.isdefault").value(DEFAULT_ISDEFAULT.booleanValue()))
            .andExpect(jsonPath("$.isdeleted").value(DEFAULT_ISDELETED.booleanValue()))
            .andExpect(jsonPath("$.domainsid").value(DEFAULT_DOMAINSID.toString()))
            .andExpect(jsonPath("$.ipaddress").value(DEFAULT_IPADDRESS.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.issecure").value(DEFAULT_ISSECURE.booleanValue()))
            .andExpect(jsonPath("$.userbasedn").value(DEFAULT_USERBASEDN.toString()))
            .andExpect(jsonPath("$.groupbasedn").value(DEFAULT_GROUPBASEDN.toString()))
            .andExpect(jsonPath("$.portype").value(DEFAULT_PORTYPE.toString()))
            .andExpect(jsonPath("$.fetchstatus").value(DEFAULT_FETCHSTATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLdapConfig() throws Exception {
        // Get the ldapConfig
        restLdapConfigMockMvc.perform(get("/api/ldapConfigs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLdapConfig() throws Exception {
        // Initialize the database
        ldapConfigRepository.saveAndFlush(ldapConfig);

		int databaseSizeBeforeUpdate = ldapConfigRepository.findAll().size();

        // Update the ldapConfig
        ldapConfig.setName(UPDATED_NAME);
        ldapConfig.setDomainname(UPDATED_DOMAINNAME);
        ldapConfig.setNetbiosdomainname(UPDATED_NETBIOSDOMAINNAME);
        ldapConfig.setWhenmanaged(UPDATED_WHENMANAGED);
        ldapConfig.setLastsynctime(UPDATED_LASTSYNCTIME);
        ldapConfig.setIsdefault(UPDATED_ISDEFAULT);
        ldapConfig.setIsdeleted(UPDATED_ISDELETED);
        ldapConfig.setDomainsid(UPDATED_DOMAINSID);
        ldapConfig.setIpaddress(UPDATED_IPADDRESS);
        ldapConfig.setPort(UPDATED_PORT);
        ldapConfig.setIssecure(UPDATED_ISSECURE);
        ldapConfig.setUserbasedn(UPDATED_USERBASEDN);
        ldapConfig.setGroupbasedn(UPDATED_GROUPBASEDN);
        ldapConfig.setPortype(UPDATED_PORTYPE);
        ldapConfig.setFetchstatus(UPDATED_FETCHSTATUS);

        restLdapConfigMockMvc.perform(put("/api/ldapConfigs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ldapConfig)))
                .andExpect(status().isOk());

        // Validate the LdapConfig in the database
        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeUpdate);
        LdapConfig testLdapConfig = ldapConfigs.get(ldapConfigs.size() - 1);
        assertThat(testLdapConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLdapConfig.getDomainname()).isEqualTo(UPDATED_DOMAINNAME);
        assertThat(testLdapConfig.getNetbiosdomainname()).isEqualTo(UPDATED_NETBIOSDOMAINNAME);
        assertThat(testLdapConfig.getWhenmanaged()).isEqualTo(UPDATED_WHENMANAGED);
        assertThat(testLdapConfig.getLastsynctime()).isEqualTo(UPDATED_LASTSYNCTIME);
        assertThat(testLdapConfig.getIsdefault()).isEqualTo(UPDATED_ISDEFAULT);
        assertThat(testLdapConfig.getIsdeleted()).isEqualTo(UPDATED_ISDELETED);
        assertThat(testLdapConfig.getDomainsid()).isEqualTo(UPDATED_DOMAINSID);
        assertThat(testLdapConfig.getIpaddress()).isEqualTo(UPDATED_IPADDRESS);
        assertThat(testLdapConfig.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testLdapConfig.getIssecure()).isEqualTo(UPDATED_ISSECURE);
        assertThat(testLdapConfig.getUserbasedn()).isEqualTo(UPDATED_USERBASEDN);
        assertThat(testLdapConfig.getGroupbasedn()).isEqualTo(UPDATED_GROUPBASEDN);
        assertThat(testLdapConfig.getPortype()).isEqualTo(UPDATED_PORTYPE);
        assertThat(testLdapConfig.getFetchstatus()).isEqualTo(UPDATED_FETCHSTATUS);
    }

    @Test
    @Transactional
    public void deleteLdapConfig() throws Exception {
        // Initialize the database
        ldapConfigRepository.saveAndFlush(ldapConfig);

		int databaseSizeBeforeDelete = ldapConfigRepository.findAll().size();

        // Get the ldapConfig
        restLdapConfigMockMvc.perform(delete("/api/ldapConfigs/{id}", ldapConfig.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LdapConfig> ldapConfigs = ldapConfigRepository.findAll();
        assertThat(ldapConfigs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
