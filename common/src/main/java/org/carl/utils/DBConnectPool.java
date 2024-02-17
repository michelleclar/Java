package org.carl.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.commons.fields.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnectPool {
  static Logger logger = LoggerFactory.getLogger(DBConnectPool.class);
  static Map<String, Map<String, HikariDataSource>> use = new HashMap<>();

  static {
    _initByMyDB(DB.driverMap);
  }

  public DBConnectPool(Map<String, Map<String, DataSource>> dbTypeAndSourceIdMap) {
    _initByMyDB(dbTypeAndSourceIdMap);
  }

  // {
  // HikariConfig hikariConfig = new HikariConfig();
  // hikariConfig.setPoolName("HikariCP 连接池");
  // hikariConfig.setDataSourceClassName("oracle.jdbc.pool.OracleDataSource");
  // hikariConfig.addDataSourceProperty("user", "test");
  // hikariConfig.addDataSourceProperty("password", "123456");
  // hikariConfig.addDataSourceProperty("url", "jdbc:oracle:thin:@10.49.196.10:1521:test");
  // hikariConfig.setMaximumPoolSize(15);
  //
  // HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
  // Connection connection = null;
  // try {
  // connection = hikariDataSource.getConnection();
  // Statement st = connection.createStatement();
  // ResultSet rs = st.executeQuery("select * from v$version");
  // if (rs.next()) {
  // logger.info(rs.getString(1));
  // }
  // } catch (SQLException e) {
  // e.printStackTrace();
  // } finally {
  // close(connection);
  // }
  //
  // // 实际使用中一般是在应用启动时初始化数据源，应用从数据源中获取连接；并不会关闭数据源。
  // hikariDataSource.close();
  // }

  static void initByMyDB(Map<String, List<String>> dbTypeAndSourceIdMap) {
    dbTypeAndSourceIdMap.forEach(
        (k, v) -> {
          String driverClassName = Driver.MYSQL;
          switch (k) {
            case DB.MYSQL -> driverClassName = Driver.MYSQL;
            case DB.POSTGRES -> driverClassName = Driver.POSTGRES;
            default -> throw new RuntimeException("not supported this driver");
          }
          for (String sourceId : v) {
            DataSource dataSource = DB.getDataSource(k, sourceId);
            HikariDataSource hikariDataSource = options(dataSource, driverClassName);
            Map<String, HikariDataSource> hikarDataSourceMap = getHikarDataSourceMap(k);
            hikarDataSourceMap.put(dataSource.getId(), hikariDataSource);
          }
        });
  }

  static void _initByMyDB(Map<String, Map<String, DataSource>> dbTypeAndSourceIdMap) {
    dbTypeAndSourceIdMap.forEach(
        (k, v) -> {
          String driverClassName = Driver.MYSQL;
          switch (k) {
            case DB.MYSQL -> driverClassName = Driver.MYSQL;
            case DB.POSTGRES -> driverClassName = Driver.POSTGRES;
            default -> throw new RuntimeException("not supported this driver");
          }
          Collection<DataSource> values = v.values();
          for (DataSource d : values) {

            HikariDataSource hikariDataSource = options(d, driverClassName);
            Map<String, HikariDataSource> hikarDataSourceMap = getHikarDataSourceMap(k);
            hikarDataSourceMap.put(d.getId(), hikariDataSource);
          }
        });
  }

  static Map<String, HikariDataSource> getHikarDataSourceMap(String driverName) {

    if (use.containsKey(driverName)) {
      return use.get(driverName);
    }

    Map<String, HikariDataSource> m = new HashMap<>();
    use.put(driverName, m);
    return use.get(driverName);
  }

  static HikariDataSource options(DataSource d, String driverClassName) {
    HikariConfig hikariConfig = new HikariConfig();
    hikariConfig.setPoolName(d.getId());
    hikariConfig.setDriverClassName(driverClassName);
    hikariConfig.setJdbcUrl(d.getJdbcUrl());
    hikariConfig.setUsername(d.getUsername());
    hikariConfig.setPassword(d.getPassword());
    hikariConfig.setMinimumIdle(2);
    hikariConfig.setMaximumPoolSize(10);
    return new HikariDataSource(hikariConfig);
  }

  void close(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static HikariDataSource getConnectionPool(String type, String id) {

    return use.get(type).get(id);
  }

  public static void watch(HikariDataSource h) {
    HikariPoolMXBean hikariPoolMXBean = h.getHikariPoolMXBean();
    logger.info("Active Connection: {}", hikariPoolMXBean.getActiveConnections());
    logger.info("Idle Connection: {}", hikariPoolMXBean.getIdleConnections());
    logger.info(
        "Threads Waiting for Connection: {}", hikariPoolMXBean.getThreadsAwaitingConnection());
  }
}