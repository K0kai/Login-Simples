package com.kokai.User;

public class User {

    private final String id;
    private String age;
    private String name;
    private String email;
    private String password;
    private final String creationDate;
    private String updateDate;

    public User(String id, String name, String password, String age, String email, String creationDate, String updateDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.age = age;
        this.email = email;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getCDate() {
        return creationDate;
    }

    public String getUDate() {
        return updateDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUDate(String uDate) {
        this.updateDate = uDate;
    }

    public void deleteById(String id) {

    }

}
