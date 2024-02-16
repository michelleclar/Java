package org.carl.test;

import org.carl.commons.config.DB;
import org.carl.commons.config.DataSource;
import org.gen.tables.daos.UserDao;
import org.gen.tables.pojos.User;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.gen.Tables.USER;
import static org.jooq.impl.DSL.count;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    public void setUp() {
        mySQLDSL = getMysqlDslContext();
        mySQLUsetDao = new UserDao(mySQLDSL.configuration());
    }

    @Test
    public void testInsert() {
        mySQLDSL
                .insertInto(USER)
                .set(USER.EMAIL, "xxxx@xx.com")
                .set(USER.PHONE_NUMBER, "123456")
                .set(USER.PASSWORD, "123445")
                .set(USER.USER_NAME, "user1")
                .returning(USER.ID)
                .execute();
    }

    @Test
    public void testSelect() {

        mySQLUsetDao.findAll();
        mySQLUsetDao.findById(1);
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
    public void testPage(){
        Result<Record2<Integer, Integer>> fetch = mySQLDSL.selectDistinct(
                        USER.ID,
                        count().over().as(TOTAL_ELEMENTS))
                .from(USER)
//            .groupBy(USER.CREATED_AT)
//            .orderBy(USER.UPDATED_AT.desc())
                .offset(0)
                .limit(10)
                .fetch();
        System.out.println(fetch);
    }
}
