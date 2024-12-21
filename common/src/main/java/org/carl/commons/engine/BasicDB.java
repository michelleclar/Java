package org.carl.commons.engine;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import jakarta.xml.bind.JAXB;
import org.carl.commons.config.DataSourceProperties;
import org.carl.commons.engine.core.DSLContextX;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.TableImpl;
import org.jooq.tools.jdbc.JDBCUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;


public class BasicDB {
    private final static Logger logger = LoggerFactory.getLogger("DB");
    //    DSLContext dsl;
    DSLContextX dsl;

    private final static String settingName = "settings.xml";
    public BasicDB(DataSourceProperties d) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("db");
        hikariConfig.setDriverClassName(d.getDriverName());
        hikariConfig.setJdbcUrl(d.getJdbcUrl());
        hikariConfig.setUsername(d.getUserName());
        hikariConfig.setPassword(d.getPassword());
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(10);
        HikariDataSource options = new HikariDataSource(hikariConfig);
        SQLDialect dialect = JDBCUtils.dialect(d.getJdbcUrl());
        File file = new File(this.getClass().getClassLoader().getResource(settingName).getPath());
        Settings settings = file.exists() ? JAXB.unmarshal(file, Settings.class) : new Settings();
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.setSQLDialect(dialect);
        configuration.setDataSource(options);
        configuration.setSettings(settings);
        configuration.setExecuteListenerProvider();
        dsl = new DSLContextX(configuration);
    }

    public <T> T get(Function<DSLContextX, T> queryFunction) {
        return queryFunction.apply(dsl);
    }

    public <T, R extends Record> T limit(Function<DSLContext, T> queryFunction, TableImpl<R> table) {
        return queryFunction.apply(dsl);
    }

    <T extends Record> Integer count(TableImpl<T> table) {
        return this.get(dsl -> dsl.fetchCount(table));
    }

    public void run(Consumer<DSLContext> queryFunction) {
        queryFunction.accept(dsl);
    }

    static Map<String, Object> dtoFactory = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T dto(Function<Configuration, T> function) {
        Configuration conf = dsl.configuration();
        String key = function.getClass().getSimpleName().split("[$]")[0];
        Object o = dtoFactory.get(key);
        if (o == null) {
            dtoFactory.put(key, function.apply(conf));
        }

        return (T) dtoFactory.get(key);
    }

    public void transaction(Consumer<DSLContext> queryFunction) {
        long start = System.currentTimeMillis();
        logger.debug("Transaction execution start time :{}", start);
        dsl.transaction(configuration -> {
            DSLContext dsl = DSL.using(configuration);
            queryFunction.accept(dsl);
        });
        logger.debug("Transaction duration: {}", System.currentTimeMillis() - start);
    }

    public void watch(HikariDataSource h) {
        HikariPoolMXBean hikariPoolMXBean = h.getHikariPoolMXBean();
        logger.info("Active Connection: {}", hikariPoolMXBean.getActiveConnections());
        logger.info("Idle Connection: {}", hikariPoolMXBean.getIdleConnections());
        logger.info("Threads Waiting for Connection: {}",
                hikariPoolMXBean.getThreadsAwaitingConnection());
    }
}
