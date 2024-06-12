package com.kokai.Services;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kokai.User.Admin;
import com.kokai.User.User;
import com.kokai.User.UserContainer;

public class JsonParser {

    public static void addUserToFile(String filePath, User newUser) throws IOException {
        boolean isAdmin = false;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        UserContainer userContainer;
        try (FileReader reader = new FileReader(filePath)) {
            userContainer = gson.fromJson(reader, new TypeToken<UserContainer>() {
            }.getType());
        }
        if (newUser instanceof Admin admin) {
            isAdmin = true;
            if (userContainer != null) {
                if (userContainer.getAdmins() != null) {
                    List<Admin> admAccounts = userContainer.getAdmins();
                    admAccounts.add(admin);
                    userContainer.setAdmins(admAccounts);
                    String json = gson.toJson(userContainer);
                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(json);
                        System.out.println("Wrote admin to file.");
                    }
                } else {
                    List<Admin> admAccounts = new ArrayList<>();
                    admAccounts.add(admin);
                    userContainer.setAdmins(admAccounts);
                    String json = gson.toJson(userContainer);
                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(json);
                        System.out.println("Wrote admin to file.");
                    }
                }
            } else {
                List<Admin> admAccounts = new ArrayList<>();
                admAccounts.add(admin);
                UserContainer newUserContainer = new UserContainer();
                newUserContainer.setAdmins(admAccounts);
                String json = gson.toJson(newUserContainer);
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(json);
                    System.out.println("Wrote admin to file.");

                }

            }

        }

        if (!isAdmin) {
            if (userContainer != null) {
                if (userContainer.getUsers() != null) {
                    List<User> userAccounts = userContainer.getUsers();
                    userAccounts.add(newUser);
                    userContainer.setUsers(userAccounts);
                    String json = gson.toJson(userContainer);
                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(json);
                        System.out.println("Wrote user to file.");
                    }
                } else {
                    List<User> userAccounts = new ArrayList<>();
                    userAccounts.add(newUser);
                    userContainer.setUsers(userAccounts);
                    String json = gson.toJson(userContainer);
                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(json);
                        System.out.println("Wrote user to file.");

                    }
                }
            } else {
                List<User> userAccounts = new ArrayList<>();
                userAccounts.add(newUser);
                UserContainer newUserContainer = new UserContainer();
                newUserContainer.setUsers(userAccounts);
                String json = gson.toJson(newUserContainer);
                try (FileWriter writer = new FileWriter(filePath)) {
                    writer.write(json);
                    System.out.println("Wrote user to file.");
                }
            }
        }
    }

    public static void modifyUserInFile(String filePath, User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        UserContainer userContainer;
        boolean isAdmin = false;
        int indexOf;
        try (FileReader reader = new FileReader(filePath)) {
            userContainer = gson.fromJson(reader, new TypeToken<UserContainer>() {
            }.getType());
        }
        if (user instanceof Admin admin) {
            isAdmin = true;
            List<Admin> admAccounts = userContainer.getAdmins();
            for (int i = 0; i < admAccounts.size(); i++) {
                if (admAccounts.get(i).getId().equals(user.getId())) {
                    indexOf = i;
                    admAccounts.set(indexOf, admin);
                    break;
                }
            }
            userContainer.setAdmins(admAccounts);
            String json = gson.toJson(userContainer);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json);
            }

        }
        if (!isAdmin) {
            List<User> userAccounts = userContainer.getUsers();
            for (int i = 0; i < userAccounts.size(); i++) {
                if (userAccounts.get(i).getId().equals(user.getId())) {
                    indexOf = i;
                    userAccounts.set(indexOf, user);
                    break;
                }
            }
            userContainer.setUsers(userAccounts);
            String json = gson.toJson(userContainer);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(json);
            }
        }

    }

    public static void removeUserFromFile(String filePath, String id) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        UserContainer userContainer;
        boolean found = false;
        try (FileReader reader = new FileReader(filePath)) {
            userContainer = gson.fromJson(reader, new TypeToken<UserContainer>() {
            }.getType());
        }
        List<User> userAccounts = userContainer.getUsers();
        List<Admin> admAccounts = userContainer.getAdmins();
        for (int i = 0; i < userAccounts.size(); i++) {
            if (userAccounts.get(i).getId().equals(id)) {
                found = true;
                userAccounts.remove(i);
                userContainer.setUsers(userAccounts);
                break;
            }

        }
        if (!found) {
            for (int i = 0; i < admAccounts.size(); i++) {
                if (admAccounts.get(i).getId().equals(id)) {
                    found = true;
                    admAccounts.remove(i);
                    userContainer.setAdmins(admAccounts);
                    break;
                }

            }
        }
        String json = gson.toJson(userContainer);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }

    }

    public static void removeAllFromFile(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, false)) {
            writer.write("");
        }

    }
}
