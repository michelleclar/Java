package org.carl.jooq.generator;

public class DataSourceConfig {
  String jdbcUrl;
  String userName;
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
