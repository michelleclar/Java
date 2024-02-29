package org.carl.jooq.generator;

import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ConfigProperties(prefix = "quarkus.datasource")
public class DataSourceConfig {
  @ConfigProperty(name = "jdbc.url")
  String jdbcUrl;

  @ConfigProperty(name = "username")
  String userName;

  @ConfigProperty(name = "password")
  String password;

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }
}
