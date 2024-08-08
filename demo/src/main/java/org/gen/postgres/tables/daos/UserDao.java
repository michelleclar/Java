/*
 * This file is generated by jOOQ.
 */
package org.gen.postgres.tables.daos;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.gen.postgres.tables.User;
import org.gen.postgres.tables.records.UserRecord;
import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserDao extends DAOImpl<UserRecord, org.gen.postgres.tables.pojos.User, Integer> {

    /**
     * Create a new UserDao without any configuration
     */
    public UserDao() {
        super(User.USER, org.gen.postgres.tables.pojos.User.class);
    }

    /**
     * Create a new UserDao with an attached configuration
     */
    public UserDao(Configuration configuration) {
        super(User.USER, org.gen.postgres.tables.pojos.User.class, configuration);
    }

    @Override
    public Integer getId(org.gen.postgres.tables.pojos.User object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(User.USER.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchById(Integer... values) {
        return fetch(User.USER.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public org.gen.postgres.tables.pojos.User fetchOneById(Integer value) {
        return fetchOne(User.USER.ID, value);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public Optional<org.gen.postgres.tables.pojos.User> fetchOptionalById(Integer value) {
        return fetchOptional(User.USER.ID, value);
    }

    /**
     * Fetch records that have <code>user_name BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfUserName(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.USER_NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>user_name IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByUserName(String... values) {
        return fetch(User.USER.USER_NAME, values);
    }

    /**
     * Fetch records that have <code>email BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfEmail(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.EMAIL, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>email IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByEmail(String... values) {
        return fetch(User.USER.EMAIL, values);
    }

    /**
     * Fetch a unique record that has <code>email = value</code>
     */
    public org.gen.postgres.tables.pojos.User fetchOneByEmail(String value) {
        return fetchOne(User.USER.EMAIL, value);
    }

    /**
     * Fetch a unique record that has <code>email = value</code>
     */
    public Optional<org.gen.postgres.tables.pojos.User> fetchOptionalByEmail(String value) {
        return fetchOptional(User.USER.EMAIL, value);
    }

    /**
     * Fetch records that have <code>password BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfPassword(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.PASSWORD, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>password IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByPassword(String... values) {
        return fetch(User.USER.PASSWORD, values);
    }

    /**
     * Fetch records that have <code>phone BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfPhone(String lowerInclusive, String upperInclusive) {
        return fetchRange(User.USER.PHONE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>phone IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByPhone(String... values) {
        return fetch(User.USER.PHONE, values);
    }

    /**
     * Fetch records that have <code>version BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfVersion(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(User.USER.VERSION, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>version IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByVersion(Integer... values) {
        return fetch(User.USER.VERSION, values);
    }

    /**
     * Fetch records that have <code>created_at BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfCreatedAt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(User.USER.CREATED_AT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>created_at IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByCreatedAt(LocalDateTime... values) {
        return fetch(User.USER.CREATED_AT, values);
    }

    /**
     * Fetch records that have <code>updated_at BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchRangeOfUpdatedAt(LocalDateTime lowerInclusive, LocalDateTime upperInclusive) {
        return fetchRange(User.USER.UPDATED_AT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>updated_at IN (values)</code>
     */
    public List<org.gen.postgres.tables.pojos.User> fetchByUpdatedAt(LocalDateTime... values) {
        return fetch(User.USER.UPDATED_AT, values);
    }
}
