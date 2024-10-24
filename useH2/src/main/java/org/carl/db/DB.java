package org.carl.db;

import org.carl.db.commons.config.DataSourceProperties;
import org.carl.db.commons.engine.BasicDB;

public class DB extends BasicDB {
    public DB() {
        super(new DataSourceProperties().setJdbcUrl("jdbc:h2:mem:myh2").setPassword("root").setUserName("root").setDriverName("org.h2.Driver"));
    }

}