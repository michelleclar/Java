package org.carl.jooq;

import org.jooq.RecordContext;
import org.jooq.impl.DefaultRecordListener;

/**
 * JooqInsertListener
 * <p>
 * can be used to customize the inserting and may help with autogenerated ids.
 * </p>
 */
public class JooqInsertListener extends DefaultRecordListener {

    @Override
    public void insertStart(RecordContext ctx) {
        // TODO: check if we can prepare snowflake-ids / UUIDs here.
        // Generate an ID for inserted BOOKs
        /*if (ctx.record() instanceof BookRecord) {
            BookRecord book = (BookRecord) ctx.record();
            book.setId(IDTools.generate());
        }*/
    }
}
