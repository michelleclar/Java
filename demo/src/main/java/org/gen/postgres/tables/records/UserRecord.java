/*
 * This file is generated by jOOQ.
 */
package org.gen.postgres.tables.records;


import java.time.LocalDateTime;

import org.gen.postgres.tables.User;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> implements Record8<Integer, String, String, String, String, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.user.user_name</code>.
     */
    public void setUserName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user.user_name</code>.
     */
    public String getUserName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.user.email</code>.
     */
    public void setEmail(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user.email</code>.
     */
    public String getEmail() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.user.password</code>.
     */
    public void setPassword(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user.password</code>.
     */
    public String getPassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.user.phone</code>.
     */
    public void setPhone(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user.phone</code>.
     */
    public String getPhone() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.user.version</code>.
     */
    public void setVersion(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user.version</code>.
     */
    public Integer getVersion() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>public.user.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.user.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>public.user.updated_at</code>.
     */
    public void setUpdatedAt(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.user.updated_at</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return (LocalDateTime) get(7);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row8<Integer, String, String, String, String, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    @Override
    public Row8<Integer, String, String, String, String, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row8) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return User.USER.ID;
    }

    @Override
    public Field<String> field2() {
        return User.USER.USER_NAME;
    }

    @Override
    public Field<String> field3() {
        return User.USER.EMAIL;
    }

    @Override
    public Field<String> field4() {
        return User.USER.PASSWORD;
    }

    @Override
    public Field<String> field5() {
        return User.USER.PHONE;
    }

    @Override
    public Field<Integer> field6() {
        return User.USER.VERSION;
    }

    @Override
    public Field<LocalDateTime> field7() {
        return User.USER.CREATED_AT;
    }

    @Override
    public Field<LocalDateTime> field8() {
        return User.USER.UPDATED_AT;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getUserName();
    }

    @Override
    public String component3() {
        return getEmail();
    }

    @Override
    public String component4() {
        return getPassword();
    }

    @Override
    public String component5() {
        return getPhone();
    }

    @Override
    public Integer component6() {
        return getVersion();
    }

    @Override
    public LocalDateTime component7() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime component8() {
        return getUpdatedAt();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getUserName();
    }

    @Override
    public String value3() {
        return getEmail();
    }

    @Override
    public String value4() {
        return getPassword();
    }

    @Override
    public String value5() {
        return getPhone();
    }

    @Override
    public Integer value6() {
        return getVersion();
    }

    @Override
    public LocalDateTime value7() {
        return getCreatedAt();
    }

    @Override
    public LocalDateTime value8() {
        return getUpdatedAt();
    }

    @Override
    public UserRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UserRecord value2(String value) {
        setUserName(value);
        return this;
    }

    @Override
    public UserRecord value3(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserRecord value4(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UserRecord value5(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public UserRecord value6(Integer value) {
        setVersion(value);
        return this;
    }

    @Override
    public UserRecord value7(LocalDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public UserRecord value8(LocalDateTime value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public UserRecord values(Integer value1, String value2, String value3, String value4, String value5, Integer value6, LocalDateTime value7, LocalDateTime value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer id, String userName, String email, String password, String phone, Integer version, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(User.USER);

        setId(id);
        setUserName(userName);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setVersion(version);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(org.gen.postgres.tables.pojos.User value) {
        super(User.USER);

        if (value != null) {
            setId(value.getId());
            setUserName(value.getUserName());
            setEmail(value.getEmail());
            setPassword(value.getPassword());
            setPhone(value.getPhone());
            setVersion(value.getVersion());
            setCreatedAt(value.getCreatedAt());
            setUpdatedAt(value.getUpdatedAt());
            resetChangedOnNotNull();
        }
    }
}
