package org.carl.crud;

import org.carl.engine.DB;

import static org.carl.generated.Tables.USER;

public class Remove {
        public static DB db = new DB();

    public static int removeById(Integer id) {
        return db.get(dsl -> dsl.delete(USER).where(USER.ID.eq(id)).execute());
    }
}
