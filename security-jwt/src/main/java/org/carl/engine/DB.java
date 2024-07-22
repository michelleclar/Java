package org.carl.engine;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
@Component
public class DB {
    Logger log = LoggerFactory.getLogger(DB.class);
    private final DSLContext dsl;

    public DB(DSLContext dslContext) {
        this.dsl = dslContext;
    }

    public <T> T get(Function<DSLContext, T> queryFunction) {
        return queryFunction.apply(dsl);
    }

    public void run(Consumer<DSLContext> queryFunction) {
        queryFunction.accept(dsl);
    }

    public void transaction(Consumer<DSLContext> queryFunction) {
        long start = System.currentTimeMillis();
        log.debug("Transaction execution start time :{}", start);
        dsl.transaction(configuration -> {
            DSLContext dsl = DSL.using(configuration);
            queryFunction.accept(dsl);
        });
        log.debug("Transaction duration: {}", System.currentTimeMillis() - start);
    }


}
