package com.kokai.User;

import java.util.List;

public class UserContainer {

    private List<User> user;
    private List<Admin> admin;

    public List<User> getUsers() {
        return user;
    }

    public void setUsers(List<User> user) {
        this.user = user;
    }

    public List<Admin> getAdmins() {
        return admin;
    }

    public void setAdmins(List<Admin> admin) {
        this.admin = admin;
    }
}
