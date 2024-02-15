package org.carl.commons.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.carl.utils.MapToBeanConverter;
import org.yaml.snakeyaml.Yaml;

public class DB {
  // NOTE: Driver --> connection config(key,)
  public static Map<String, Map<String, DataSource>> driverMap = new HashMap<>();

  static {
    Yaml yaml = new Yaml();
    Map<String, Object> configMap =
        yaml.load(DB.class.getClassLoader().getResourceAsStream("DB.yml"));

    List<Map<String, Object>> o = (ArrayList<Map<String, Object>>) configMap.get("DB");
    o.forEach(
        v -> {
          DataSource dataSource = MapToBeanConverter.convert(v, DataSource.class);
          getDataSourceMap((String) v.get("Driver")).put(dataSource.getId(), dataSource);
        });
    System.out.println(driverMap);
  }

  public static DataSource getMysqlDataSourceById(String id) {
    return driverMap.get(MYSQL).get(id);
  }

  public static DataSource getDataSource(String type, String id) {

    return driverMap.get(type).get(id);
  }

  static Map<String, DataSource> getDataSourceMap(String driverName) {

    if (driverMap.containsKey(driverName)) {
      return driverMap.get(driverName);
    }

    Map<String, DataSource> m = new HashMap<>();
    driverMap.put(driverName, m);
    return driverMap.get(driverName);
  }

  public static final String MYSQL = "mysql";

  public static void main(String[] args) {}
}
