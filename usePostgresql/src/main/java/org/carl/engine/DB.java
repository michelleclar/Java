package org.carl.engine;

import org.carl.commons.config.DataSourceProperties;
import org.carl.commons.engine.BasicDB;

public class DB extends BasicDB {
    public DB() {
        super(new DataSourceProperties().setJdbcUrl("jdbc:postgresql://localhost:15432/db").setPassword("root").setUserName("root").setDriverName("org.postgresql.Driver"));
    }

    DB(DataSourceProperties d) {
        super(d);
    }
}
