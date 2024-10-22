package org.carl.crud;

import org.carl.engine.DB;

import static org.carl.generated.Tables.USER;

public class Select {
    public static DB db = new DB();

    public static int findById(Integer id) {
        return db.get(dsl -> dsl.selectFrom(USER).where(USER.ID.eq(id)).execute());
    }
}
