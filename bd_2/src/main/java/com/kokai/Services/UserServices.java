package com.kokai.Services;

import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.kokai.User.Admin;
import com.kokai.User.User;
import com.kokai.User.UserContainer;

public class UserServices {

    private final static String jsonPath = "src\\main\\java\\com\\kokai\\storage\\data1.json";

    public static boolean registerUser() throws Exception {
        Gson gson = new Gson();
        FileReader fr = new FileReader(jsonPath);
        UserContainer userContainer = gson.fromJson(fr, UserContainer.class);
        Console input = System.console();
        String id = "", newId;
        int permLevel = 0;
        String name = "", password = "", email = "", age, formattedAge = null, cDate, uDate;
        boolean idOk = false, nameOk = false, passOk = false, emailOk = false, ageOk = false, permOk = false;
        while (true) {
            while (!permOk) {
                System.out.println("\nEnter user permission level\nPermission levels vary from 0 to 2, 0 being common user and 2 being administrator\n");
                permLevel = Integer.parseInt(input.readLine("Permission level:"));
                if (permLevel >= 0 && permLevel <= 2) {
                    permOk = true;
                } else {
                    System.out.println("Invalid permission level.");
                }
            }
            while (!nameOk) {
                name = input.readLine("Username: ");
                if (name.equalsIgnoreCase("exit")) {
                    return false;
                }
                if (name.isBlank()) {
                    System.out.println("Username cannot be empty.\n");
                } else {
                    nameOk = true;
                }
            }
            while (!passOk) {
                password = String.valueOf(input.readPassword("Password: "));
                if (password.equalsIgnoreCase("exit")) {
                    return false;
                }
                if (password.isBlank()) {
                    System.out.println("Password cannot be empty.\n");
                } else {
                    passOk = true;
                }
            }
            while (!ageOk) {
                age = input.readLine("Date of Birth(day/month/year): ");
                if (age.equalsIgnoreCase("exit")) {
                    return false;
                }
                if (!age.isBlank()) {
                    formattedAge = DateTimeFunctions.formatDate(age);
                    if (formattedAge == null) {
                        System.out.println("Invalid date format.\n");
                    } else {
                        ageOk = true;
                    }
                } else {
                    System.out.println("Age cannot be empty.\n");
                }
            }
            while (!emailOk) {
                email = input.readLine("Email: ");
                if (email.equalsIgnoreCase("exit")) {
                    return false;
                }
                if (email.isBlank()) {
                    System.out.println("Email cannot be empty.\n");
                } else {
                    emailOk = true;
                }
            }
            while (!idOk) {
                if (permLevel < 1) {
                    newId = generateID.generateProceduralID(10, true);
                    while (checkUser.checkIDCollision(userContainer, newId) > 0) {
                        if (checkUser.checkIDCollision(userContainer, newId) == 2) {
                            System.out.println("Error ocurred while generating Id");
                            return false;
                        }
                        newId = generateID.generateProceduralID(10, true);

                    }
                    id = newId;
                    idOk = true;
                } else if (permLevel > 0) {
                    newId = generateID.generateProceduralID(10, false);
                    while (checkUser.checkIDCollision(userContainer, newId) > 0) {
                        if (checkUser.checkIDCollision(userContainer, newId) == 2) {
                            System.out.println("Error ocurred while generating Id");
                            return false;
                        }
                        newId = generateID.generateProceduralID(10, false);

                    }
                    id = newId;
                    idOk = true;
                }
            }
            if (idOk) {
                if (!id.isBlank()) {
                    switch (checkUser.checkNameCollision(userContainer, id, name)) {
                        case 1 -> {
                            System.out.println("Name is already taken.");
                            nameOk = false;
                        }
                        case 0 -> {
                        }
                        default -> {
                            System.out.println("Checkage error");
                            nameOk = false;
                        }
                    }

                } else {
                    System.out.println("Id is blank.");
                    idOk = false;
                }
                switch (checkUser.checkEmailCollision(userContainer, id, email)) {
                    case 1 -> {
                        System.out.println("Email is already registered.");
                        emailOk = false;
                    }
                    case 0 -> {
                    }
                    default -> {
                        System.out.println("Checkage error");
                        emailOk = false;
                    }
                }

            }
            if (idOk && permOk && nameOk && passOk && ageOk && emailOk) {
                cDate = DateTimeFunctions.getFormattedLocalDate();
                uDate = DateTimeFunctions.getFormattedLocalDate();
                User newUser = new User(id, name, password, formattedAge, email, cDate, uDate);
                if (id.charAt(0) == 'u') {
                    JsonParser.addUserToFile(jsonPath, newUser);
                    System.out.println("User registered!");
                    return true;
                } else if (id.charAt(0) == 'a') {
                    Admin newAdmin = new Admin(id, name, password, formattedAge, email, cDate, uDate, permLevel);
                    JsonParser.addUserToFile(jsonPath, newAdmin);
                    System.out.println("Admin registered!");
                    return true;
                }
            }

        }
    }

    public static void modifyUser(String id) throws Exception {
        User user = null;
        Admin admin = null;
        switch (id.charAt(0)) {
            case 'u' ->
                user = (User) UserServices.getUserFromId(id);
            case 'a' ->
                admin = (Admin) UserServices.getUserFromId(id);
            default -> {
                System.out.println("Invalid Id, closing method.\n");
                return;
            }
        }
        Console input = System.console();
        int permLevel = 0;
        String name = "", password = "", email = "", age = null, formattedAge = null;
        boolean nameOk = false, passOk = false, emailOk = false, ageOk = false, permOk = false;
        System.out.println("-----------------------------------------------------\nFor a field you don't want to modify, leave it blank.\n-----------------------------------------------------");
        while (true) {
            while (!permOk) {
                System.out.println("\nEnter user permission level\nPermission levels vary from 0 to 2, 0 being common user and 2 being administrator\n");
                String lastInput = input.readLine("Permission level: ");
                if (lastInput.isBlank()) {
                    permLevel = -1;
                } else {
                    permLevel = Integer.parseInt(lastInput);
                }

                if ((permLevel >= 0 && permLevel <= 2) || permLevel == -1) {
                    permOk = true;
                } else {
                    System.out.println("Invalid permission level.");
                }
            }
            while (!nameOk) {
                name = input.readLine("Username: ");
                if (name.equalsIgnoreCase("exit")) {
                    return;
                }
                nameOk = true;
            }
            while (!passOk) {
                password = String.valueOf(input.readPassword("Password: "));
                if (password.equalsIgnoreCase("exit")) {
                    return;
                }
                passOk = true;

            }
            while (!ageOk) {
                age = input.readLine("Date of Birth(day/month/year): ");
                if (!age.isBlank() && !age.equalsIgnoreCase("exit")) {
                    formattedAge = DateTimeFunctions.formatDate(age);
                    if (formattedAge != null) {
                        age = formattedAge;
                        ageOk = true;
                        break;
                    } else {
                        ageOk = false;
                    }
                } else if (age.isBlank() && !age.equalsIgnoreCase("exit")) {
                    ageOk = true;
                }
                if (age.equalsIgnoreCase("exit")) {
                    return;
                }
            }
            while (!emailOk) {
                email = input.readLine("Email: ");
                if (email.equalsIgnoreCase("exit")) {
                    return;
                }
                emailOk = true;

            }
            if (permOk && nameOk && passOk && ageOk && emailOk) {
                if (admin != null) {
                    if (name.isBlank()) {
                        name = admin.getName();
                    }
                    if (password.isBlank()) {
                        password = admin.getPassword();
                    }
                    if (age.isBlank()) {
                        age = admin.getAge();
                    }
                    if (email.isBlank()) {
                        email = admin.getEmail();
                    }
                    if (permLevel == -1) {
                        permLevel = admin.getPermLevel();
                    } else if (permLevel < 1) {
                        id = generateID.generateProceduralID(10, true);
                        user = new User(id, name, password, age, email, admin.getCDate(), DateTimeFunctions.getFormattedLocalDate());
                        JsonParser.addUserToFile(jsonPath, user);
                        JsonParser.removeUserFromFile(jsonPath, admin.getId());
                        return;
                    }
                    admin = new Admin(admin.getId(), name, password, age, email, admin.getCDate(), DateTimeFunctions.getFormattedLocalDate(), permLevel);
                    JsonParser.modifyUserInFile(jsonPath, admin);
                    return;

                }
                if (user != null) {
                    if (name.isBlank()) {
                        name = user.getName();
                    }
                    if (password.isBlank()) {
                        password = user.getPassword();
                    }
                    if (age.isBlank()) {
                        age = user.getAge();
                    }
                    if (email.isBlank()) {
                        email = user.getEmail();
                    }
                    if (permLevel == -1) {
                    } else if (permLevel > 0) {
                        id = generateID.generateProceduralID(10, false);
                        admin = new Admin(id, name, password, age, email, user.getCDate(), DateTimeFunctions.getFormattedLocalDate(), permLevel);
                        JsonParser.addUserToFile(jsonPath, admin);
                        JsonParser.removeUserFromFile(jsonPath, user.getId());
                        return;
                    }
                    user = new User(user.getId(), name, password, age, email, user.getCDate(), DateTimeFunctions.getFormattedLocalDate());
                    JsonParser.modifyUserInFile(jsonPath, user);
                    return;
                }

            }
        }

    }

    public static String getIdFrom(String data) throws IOException {
        Gson gson = new Gson();
        FileReader fr = new FileReader(jsonPath);
        UserContainer userContainer = gson.fromJson(fr, UserContainer.class);
        if (userContainer == null) {
            System.out.println("Database is null.");
            return "";
        }
        String id;
        for (User us : userContainer.getUsers()) {
            if (us.getName().equals(data) || us.getEmail().equals(data)) {
                id = us.getId();
                return id;
            }
        }
        for (Admin ad : userContainer.getAdmins()) {
            if (ad.getName().equals(data) || ad.getEmail().equals(data)) {
                id = ad.getId();
                return id;
            }
        }
        id = "null";
        System.out.printf(" Id not found, returning \"%s\" ", id);
        return id;
    }

    public static Object getUserFromId(String id) throws IOException {
        Gson gson = new Gson();
        FileReader fr = new FileReader(jsonPath);
        UserContainer userContainer = gson.fromJson(fr, UserContainer.class);
        if (userContainer == null) {
            System.out.println("Database is null.");
            return "";
        }
        switch (id.charAt(0)) {
            case 'u' -> {
                List<User> users = userContainer.getUsers();
                for (User us : users) {
                    if (us.getId().equals(id)) {
                        return us;
                    }
                }
            }
            case 'a' -> {
                List<Admin> admins = userContainer.getAdmins();
                for (User ad : admins) {
                    if (ad.getId().equals(id)) {
                        return ad;
                    }
                }
            }
            default -> {
                System.out.println(" Invalid id, returning null ");
            }
        }
        return null;
    }

    public static boolean validateUser(User user, String password) {
        if (user != null) {
            if (password.equals(user.getPassword())) {
                String userOrAdmin;
                boolean isAdmin = user instanceof Admin;
                if (isAdmin) {
                    userOrAdmin = "admin";
                } else {
                    userOrAdmin = "user";
                }
                System.out.printf(" Authenticated %s %s ", userOrAdmin, user.getName());
                return true;
            }
            System.out.println(" Failed to authenticate user. ");
            return false;
        }
        System.out.println(" User is null. ");
        return false;
    }
}
