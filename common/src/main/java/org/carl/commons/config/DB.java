package org.carl.commons.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.carl.commons.fields.Fields;
import org.carl.utils.MapToBeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class DB {
    public static Map<String, Map<String, DataSource>> driverMap = new HashMap<>();


    static {
        Yaml yaml = new Yaml();
        Map<String, Object> configMap =
                yaml.load(DB.class.getClassLoader().getResourceAsStream(Fields.DBCONFIG));

        List<Map<String, Object>> o = (List<Map<String, Object>>) configMap.get(Fields.DB);
        o.forEach(
                v -> {
                    DataSource dataSource = MapToBeanConverter.convert(v, DataSource.class);
                    getDataSourceMap((String) v.get("Driver")).put(dataSource.getId(), dataSource);
                });
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

    //NOTE:add new DB
    public static final String MYSQL = "mysql";

    public static final String POSTGRES = "postgres";

    public static final String MARIADB = "mariadb";

    public static void main(String[] args) {
    }

    public static Map<String, List<String>> getDBTypeAndSourceIdMap() {

        return null;
    }
}
