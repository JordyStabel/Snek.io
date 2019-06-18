package com.s3.snekio.restserver.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class User {

    @Id
    public ObjectId _id;

    public String userName;
    public String email;
    public String password;

    public User() {
    }

    public User(ObjectId _id, String userName, String email, String password) {
        this._id = _id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * FOR DEBUGGING ONLY!
     * @return A user object as text (with password in plain text :))
     */
    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
