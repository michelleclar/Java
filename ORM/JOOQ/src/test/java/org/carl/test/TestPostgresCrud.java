package org.carl.test;

import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.generated.tables.daos.UsersDao;
import org.carl.generated.tables.pojos.Users;
import org.carl.utils.DBConnectPool;

import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.carl.generated.Tables.USERS;
import static org.jooq.impl.DSL.count;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPostgresCrud {
  private static final String TOTAL_ELEMENTS = "total_elements";
  DSLContext dsl;

  UsersDao userDao;

  DSLContext getDslContext(DataSource dataSource) {
    return DSL.using(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
  }

  @BeforeEach
  public void setUp() throws SQLException {
    dsl = DSL.using(DBConnectPool.getConnectionPool(DB.POSTGRES, "db2").getConnection(),
        SQLDialect.POSTGRES);
    userDao = new UsersDao(dsl.configuration());
  }

  @Test
  public void testInsert() {
    dsl.insertInto(USERS).set(USERS.EMAIL, "xxxx@xx.com")
       .set(USERS.USERNAME, "user1").returning(USERS.ID).execute();
  }

  @Test
  public void testSelect() throws InterruptedException {

    for (int i = 0; i < 10; i++) {
      List<Users> all = userDao.findAll();
      DBConnectPool.watch(DBConnectPool.getConnectionPool(DB.MYSQL, "db1"));
      TimeUnit.SECONDS.sleep(3);
    }
  }

  @Test
  public void testUpdate() throws IOException {
    Users oldUser = userDao.findById(1);

    assertNotNull(oldUser);
    oldUser.setEmail("12343@xx.com");
    userDao.update(oldUser);
    Users newUser = userDao.findById(1);
    int i = System.in.read();
  }

  @Test
  public void testDelete() {
    userDao.deleteById(1);
  }

  @Test
  public void testPage() {
    Result<Record2<Integer, Integer>> fetch =
        dsl.selectDistinct(USERS.ID, count().over().as(TOTAL_ELEMENTS)).from(USERS)
            // .groupBy(USER.CREATED_AT)
            // .orderBy(USER.UPDATED_AT.desc())
            .offset(0).limit(10).fetch();
    System.out.println(fetch);
  }
}
