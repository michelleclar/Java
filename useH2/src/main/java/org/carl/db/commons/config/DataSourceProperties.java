package org.carl.db.commons.config;

public class DataSourceProperties {
  String jdbcUrl;
  String userName;
  String password;
  String driverName;

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public DataSourceProperties setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
    return this;
  }

  public String getUserName() {
    return userName;
  }

  public DataSourceProperties setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public DataSourceProperties setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getDriverName() {
    return driverName;
  }

  public DataSourceProperties setDriverName(String driverName) {
    this.driverName = driverName;
    return this;
  }
}
