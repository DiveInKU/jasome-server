// package com.diveinku.jasome.src.config;
//
// import com.zaxxer.hikari.HikariDataSource;
// import lombok.extern.slf4j.Slf4j;
// import org.h2.tools.Server;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import javax.sql.DataSource;
// import java.sql.SQLException;
//
// @Slf4j
// @Configuration
// public class H2ServerConfig {
//
//     @Bean
//     @ConfigurationProperties(prefix = "spring.datasource.hikari")
//     public DataSource dataSource() throws SQLException {
//         System.out.println("h2 start...");
//         Server server = Server.createTcpServer(
//                 "-tcp",
//                 "-tcpAllowOthers",
//                 "-tcpPort",
//                 "9093"
//         ).start();
//         if(server.isRunning(true)){
//             log.info("h2 server url = {}", server.getURL());
//         }else{
//             log.error("h2 failed..");
//         }
//         return DataSourceBuilder.create().build();
//     }
// }
