package org.carl.test;

import static org.gen.Tables.USER;
import static org.jooq.impl.DSL.count;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.carl.utils.DBConnectPool;
import org.gen.tables.daos.UserDao;
import org.gen.tables.pojos.User;
import org.gen.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCrud {
  private static final String TOTAL_ELEMENTS = "total_elements";
  DSLContext mySQLDSL;

  UserDao mySQLUsetDao;

  DSLContext getDslContext(DataSource dataSource) {
    return DSL.using(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
  }

  DSLContext getMysqlDslContext() {

    return getDslContext(DB.getDataSource(DB.MYSQL, "db1"));
  }

  @BeforeEach
  public void setUp() throws SQLException {
    // mySQLDSL = getMysqlDslContext();
    // mySQLUsetDao = new UserDao(mySQLDSL.configuration());

    DSL.using(DBConnectPool.getConnectionPool(DB.MYSQL, "db1").getConnection(), SQLDialect.MYSQL);
    mySQLDSL = DSL.using(DBConnectPool.getConnectionPool(DB.MYSQL, "db1").getConnection(),
        SQLDialect.MYSQL);
    mySQLUsetDao = new UserDao(mySQLDSL.configuration());
  }

  @Test
  public void testInsert() {
    mySQLDSL.insertInto(USER).set(USER.EMAIL, "xxxx@xx.com").set(USER.PHONE_NUMBER, "123456")
        .set(USER.PASSWORD, "123445").set(USER.USER_NAME, "user1").returning(USER.ID).execute();
  }

  @Test
  public void testSelect() throws InterruptedException {

      for (int i = 0; i < 10; i++) {
          List<User> all = mySQLUsetDao.findAll();
          DBConnectPool.watch(DBConnectPool.getConnectionPool(DB.MYSQL, "db1"));
          Thread.sleep(3);
      }


  }

  @Test
  public void testUpdate() {

    User user = mySQLUsetDao.findById(1);

    assertNotNull(user);
    user.setEmail("1312@xx.com");
    mySQLUsetDao.update(user);
  }

  @Test
  public void testDelete() {
    mySQLUsetDao.deleteById(1);
  }

  @Test
  public void testPage() {
    Result<Record2<Integer, Integer>> fetch =
        mySQLDSL.selectDistinct(USER.ID, count().over().as(TOTAL_ELEMENTS)).from(USER)
            // .groupBy(USER.CREATED_AT)
            // .orderBy(USER.UPDATED_AT.desc())
            .offset(0).limit(10).fetch();
    System.out.println(fetch);
  }
}
