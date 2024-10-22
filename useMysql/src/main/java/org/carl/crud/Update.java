package org.carl.crud;

import org.carl.engine.DB;

import static org.carl.generated.Tables.USER;

public class Update {
    public static DB db = new DB();

    public static int updateById(Integer id) {
        return db.get(dsl -> dsl.update(USER).set(USER.USER_NAME, "aaa").where(USER.ID.eq(id)).execute());
    }
}
