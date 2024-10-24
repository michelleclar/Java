package org.carl.db;

import org.junit.jupiter.api.Test;

import static org.jooq.impl.SQLDataType.INTEGER;
import static org.jooq.impl.SQLDataType.VARCHAR;

class DBTest {
    public static DB db = new DB();
    @Test
    void create() {
      db.run(dsl->{dsl.createTable("user").column("id", INTEGER).column("name", VARCHAR(20)).primaryKey("id").execute();
      });
    }
}