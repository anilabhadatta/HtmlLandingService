package com.example.htmllandingservice.config;

import com.example.htmllandingservice.dto.DbSettings;
import com.example.htmllandingservice.dto.VaultConfigBn;
import oracle.jdbc.pool.OracleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
@DependsOn("vaultConfig")
public class OracleConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DbSettings.class);

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    VaultConfig vaultConfig;



    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        VaultConfigBn vaultConfigBn = vaultConfig.populateVaultDataBn();
        logger.info(vaultConfigBn.toString());
        OracleDataSource ds = new OracleDataSource();
        ds.setDriverType(dbSettings.getDbDriverClassName());
        logger.info("Using Driver " + dbSettings.getDbDriverClassName());
        ds.setURL(vaultConfigBn.getDbUrl());
        logger.info("Using URL: " + vaultConfigBn.getDbUrl());
        ds.setUser(vaultConfigBn.getDbUser());
        logger.info("Using Username: " + vaultConfigBn.getDbUser());
        ds.setPassword(vaultConfigBn.getDbPassword());
        logger.info("Using Password: " + vaultConfigBn.getDbPassword());
        return ds;
    }
}