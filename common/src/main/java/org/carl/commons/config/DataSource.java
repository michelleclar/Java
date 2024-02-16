package org.carl.commons.config;

public class DataSource {
  String id;
  String jdbcUrl;
  String userName;
  String password;

  public void setId(String id) {
    this.id = id;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.userName = username;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "DataSource{" + "id='" + id + '\'' + ", jdbcUrl='" + jdbcUrl + '\'' + ", username='"
        + userName + '\'' + ", password='" + password + '\'' + '}';
  }

  public String getUsername() {
    return userName;
  }

  public String getId() {
    return id;
  }
}
