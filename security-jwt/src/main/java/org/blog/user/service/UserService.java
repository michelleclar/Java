package org.blog.user.service;

import org.blog.user.model.User;
import org.carl.engine.DB;
import org.carl.generated.tables.records.UsersRecord;
import org.carl.util.MD5;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.carl.generated.Tables.USERS;

@Repository
public class UserService {
    final DB DB;

    public UserService(DB db) {
        this.DB = db;
    }

    public Optional<User> findByUsername(String username) {
        return DB.get(dsl -> dsl.selectFrom(USERS).where(USERS.USERNAME.eq(username)).fetchOptionalInto(User.class));
    }

    public User register(User user) {
        user.setPassword(MD5.encode(user.getPassword()));
        DB.get(dsl -> {
            UsersRecord usersRecord = new UsersRecord(user);
            return dsl.insertInto(USERS).set(usersRecord).execute();
        });
        return user;
    }

    public User login(User user) {
        return DB.get(dsl -> dsl.selectFrom(USERS).where(USERS.USERNAME.eq(user.getUsername())).and(USERS.PASSWORD.eq(MD5.encode(user.getPassword())))
                .fetchOneInto(User.class));
    }

}
