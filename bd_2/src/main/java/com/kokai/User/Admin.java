package com.kokai.User;

public class Admin extends User {

    private int permissionLevel;

    public Admin(String id, String name, String password, String age, String email, String creationDate, String updateDate, int permissionLevel) throws InvalidAdminPermissionLevel {
        super(id, name, password, age, email, creationDate, updateDate);
        if (permissionLevel < 1 || permissionLevel > 2) {
            throw new InvalidAdminPermissionLevel("Permission level set for admin is invalid");
        } else {
            this.permissionLevel = permissionLevel;
        }

    }

    public int getPermLevel() {
        return permissionLevel;
    }

    public void setPermLevel(int permissionLevel) throws InvalidAdminPermissionLevel {
        if (permissionLevel < 1 || permissionLevel > 2) {
            throw new InvalidAdminPermissionLevel("Permission level set for admin is invalid");
        } else {
            this.permissionLevel = permissionLevel;
        }
    }

}

class InvalidAdminPermissionLevel extends Exception {

    public InvalidAdminPermissionLevel(String message) {
        super(message);
    }
}
