package org.carl.engine;

import org.carl.commons.config.DataSourceProperties;
import org.carl.commons.engine.BasicDB;

public class DB extends BasicDB {
    public DB() {
        super(new DataSourceProperties().setJdbcUrl("jdbc:mysql://localhost:13306/db").setPassword("root").setUserName("root").setDriverName("com.mysql.cj.jdbc.Driver"));
    }

    DB(DataSourceProperties d) {
        super(d);
    }
}
