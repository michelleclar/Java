package org.carl.crud;

import org.carl.engine.DB;
import org.carl.generated.tables.pojos.User;
import org.carl.generated.tables.records.UserRecord;

import static org.carl.generated.Tables.USER;

public class Insert {
    public static DB db = new DB();

    public static int insertUser(User user) {
        return db.get(dsl -> dsl.insertInto(USER).set(new UserRecord(user)).execute());
    }
}
