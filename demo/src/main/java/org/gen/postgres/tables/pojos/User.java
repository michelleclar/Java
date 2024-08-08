/*
 * This file is generated by jOOQ.
 */
package org.gen.postgres.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User() {}

    public User(User value) {
        this.id = value.id;
        this.userName = value.userName;
        this.email = value.email;
        this.password = value.password;
        this.phone = value.phone;
        this.version = value.version;
        this.createdAt = value.createdAt;
        this.updatedAt = value.updatedAt;
    }

    public User(
        Integer id,
        String userName,
        String email,
        String password,
        String phone,
        Integer version,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Getter for <code>public.user.id</code>.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter for <code>public.user.id</code>.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Getter for <code>public.user.user_name</code>.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Setter for <code>public.user.user_name</code>.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for <code>public.user.email</code>.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Setter for <code>public.user.email</code>.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for <code>public.user.password</code>.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Setter for <code>public.user.password</code>.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for <code>public.user.phone</code>.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Setter for <code>public.user.phone</code>.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for <code>public.user.version</code>.
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * Setter for <code>public.user.version</code>.
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Getter for <code>public.user.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Setter for <code>public.user.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Getter for <code>public.user.updated_at</code>.
     */
    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Setter for <code>public.user.updated_at</code>.
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (this.id == null) {
            if (other.id != null)
                return false;
        }
        else if (!this.id.equals(other.id))
            return false;
        if (this.userName == null) {
            if (other.userName != null)
                return false;
        }
        else if (!this.userName.equals(other.userName))
            return false;
        if (this.email == null) {
            if (other.email != null)
                return false;
        }
        else if (!this.email.equals(other.email))
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        }
        else if (!this.password.equals(other.password))
            return false;
        if (this.phone == null) {
            if (other.phone != null)
                return false;
        }
        else if (!this.phone.equals(other.phone))
            return false;
        if (this.version == null) {
            if (other.version != null)
                return false;
        }
        else if (!this.version.equals(other.version))
            return false;
        if (this.createdAt == null) {
            if (other.createdAt != null)
                return false;
        }
        else if (!this.createdAt.equals(other.createdAt))
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        }
        else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        result = prime * result + ((this.userName == null) ? 0 : this.userName.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
        result = prime * result + ((this.version == null) ? 0 : this.version.hashCode());
        result = prime * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = prime * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User (");

        sb.append(id);
        sb.append(", ").append(userName);
        sb.append(", ").append(email);
        sb.append(", ").append(password);
        sb.append(", ").append(phone);
        sb.append(", ").append(version);
        sb.append(", ").append(createdAt);
        sb.append(", ").append(updatedAt);

        sb.append(")");
        return sb.toString();
    }
}
