package com.kokai;
// Banco de dados 04/06/2024
// @Kokai

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.kokai.Services.DateTimeFunctions;
import com.kokai.Services.JsonParser;
import com.kokai.Services.StringFormatter;
import com.kokai.Services.UserServices;
import com.kokai.User.Admin;
import com.kokai.User.User;

public class MainApp {

    private final static String jsonPath = "src\\main\\java\\com\\kokai\\storage\\data1.json";
    private final static String asciiPath = "src\\main\\java\\com\\kokai\\storage\\ascii.txt";

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        clrscr();
        boolean finished = false;
        String currentSessionId = "";
        Console input = System.console();
        showLogo();
        long endTime = System.nanoTime();
        long initTimeNanos = endTime - startTime;
        float initTime = (float) initTimeNanos / 1000000;

        System.out.printf(StringFormatter.centerTextWithLineBreaks("\nInitialized program in %.2f seconds\nCode made by Kokai\n", 90), initTime / 1000);
        StringFormatter.typeWrite(String.format("\nGood %s! What would you like to do today?", DateTimeFunctions.greetByTimeOfDay(DateTimeFunctions.getLocalHour())), 30);
        while (!finished) {
            StringFormatter.typeWrite("\n---------------------------\n1- Register new user\n2- Modify user\n3- Delete user/users\n4- Get user information\n5- Log in\n6- Log out\n0- Exit\n---------------------------\n", 15);;
            String lastInput = input.readLine("Option: ");
            switch (lastInput.charAt(0)) {
                case '0' -> {
                    StringFormatter.typeWrite("Exitting program.\nThank you for using my program! ;)", 25);
                    finished = true;
                    Thread.sleep(1000);
                    break;
                }
                case '1' -> {
                    if (currentSessionId.isBlank() || currentSessionId.charAt(0) == 'a') {
                        System.out.println("Registering...");
                        if (!UserServices.registerUser()) {
                            System.out.println("Failed to register user.");
                        } else {
                        }
                    } else {
                        System.out.println("You can't register while logged in!");
                    }
                }
                case '2' -> {

                    if (currentSessionId.isBlank()) {
                        System.out.println("You need to be logged in to access this function.");
                        break;
                    }
                    String id = UserServices.getIdFrom(input.readLine("Enter an user's username or email: "));
                    if (currentSessionId.charAt(0) == 'u' && currentSessionId.equals(id)) {
                        UserServices.modifyUser(id);
                    } else if (currentSessionId.charAt(0) == 'u' && !currentSessionId.equals(id)) {
                        System.out.println("You do not have permission to modify other users.");
                    } else if (currentSessionId.charAt(0) == 'a') {
                        UserServices.modifyUser(id);
                    }

                }
                case '3' -> {

                    if (currentSessionId.isBlank()) {
                        System.out.println("You need to be logged in to access this function.");
                        break;
                    }
                    lastInput = input.readLine("Enter username or email to delete, or type \"all\" to delete all users: ");
                    if (!lastInput.isBlank() && !lastInput.equalsIgnoreCase("all")) {
                        String id = UserServices.getIdFrom(lastInput);
                        if (currentSessionId.charAt(0) == 'u' && currentSessionId.equals(id)) {
                            JsonParser.removeUserFromFile(jsonPath, id);
                        } else if (currentSessionId.charAt(0) == 'u' && !currentSessionId.equals(id)) {
                            System.out.println("You do not have permission to delete other users.");
                        } else if (currentSessionId.charAt(0) == 'a') {
                            JsonParser.removeUserFromFile(jsonPath, id);
                        }

                    } else if (!lastInput.isBlank() && lastInput.equalsIgnoreCase("all")) {
                        if (currentSessionId.charAt(0) == 'a') {
                            String id = UserServices.getIdFrom(input.readLine("Enter admin username or email: "));
                            User user = (User) UserServices.getUserFromId(id);
                            int permLevel;
                            if (user instanceof Admin admin) {
                                permLevel = admin.getPermLevel();
                            } else {
                                System.out.println("You need to be an admin to do this.");
                                break;
                            }
                            if (permLevel > 1 && id.equals(user.getId())) {
                                JsonParser.removeAllFromFile(jsonPath);
                            } else {
                                System.out.println("You don't have permission to do this.");
                            }
                        } else {
                            System.out.println("You need to be an admin to do this.");
                            break;
                        }
                    }

                }
                case '4' -> {

                    while (true) {
                        if (currentSessionId.isBlank()) {
                            System.out.println("You need to be logged in to access this function.");
                            break;
                        }
                        if (currentSessionId.charAt(0) == 'u') {
                            lastInput = input.readLine("Enter an user's username or email: ");
                            if (lastInput.equalsIgnoreCase("exit")){
                                break;
                            }
                            String id = UserServices.getIdFrom(lastInput);
                            if (currentSessionId.equals(id)) {
                                User user = (User) UserServices.getUserFromId(id);
                                String text = String.format("\nUsername: %s\nEmail: %s\nAge: %d\nRegister date: %s\nUpdate date: %s\n", user.getName(), user.getEmail(), DateTimeFunctions.getAgeFromDate(DateTimeFunctions.stringToDate(user.getAge())), user.getCDate(), user.getUDate());
                                StringFormatter.typeWrite(text, 30);
                                break;
                            } else {
                                System.out.println("You do not have permission to access other users' information.");
                            }
                        }
                        if (!currentSessionId.isBlank() && currentSessionId.charAt(0) == 'a') {
                            lastInput = input.readLine("Enter an user's username or email: ");
                            if (lastInput.equalsIgnoreCase("exit")){
                                break;
                            }
                            String id = UserServices.getIdFrom(lastInput);
                            User user = (User) UserServices.getUserFromId(id);
                            if (user instanceof Admin admin) {
                                String text = String.format("\nId: %s\nPermission level: %d\nUsername: %s\nPassword: %s\nEmail: %s\nAge: %d\nRegister date: %s\nUpdate date: %s\n", admin.getId(), admin.getPermLevel(), admin.getName(), admin.getPassword(), admin.getEmail(), DateTimeFunctions.getAgeFromDate(DateTimeFunctions.stringToDate(admin.getAge())), admin.getCDate(), admin.getUDate());
                                StringFormatter.typeWrite(text, 30);
                                break;
                            }
                            if (user != null)  {
                                String text = String.format("\nId: %s\nUsername: %s\nPassword: %s\nEmail: %s\nAge: %d\nRegister date: %s\nUpdate date: %s\n", user.getId(), user.getName(), user.getPassword(), user.getEmail(), DateTimeFunctions.getAgeFromDate(DateTimeFunctions.stringToDate(user.getAge())), user.getCDate(), user.getUDate());
                                StringFormatter.typeWrite(text, 30);
                                break;
                            }
                            else{
                                System.out.println("User not found.");
                            }

                        }
                    }

                }
                case '5' -> {
                    String id = UserServices.getIdFrom(input.readLine("Username or Email: "));
                    User user = (User) UserServices.getUserFromId(id);
                    String pass = String.valueOf(input.readPassword("Password: "));
                    if (user != null){
                        if (UserServices.validateUser(user, pass)) {
                            currentSessionId = user.getId();
                            StringFormatter.typeWrite("You are logged in!", 20);
                        } else {
                            System.out.println("Login information is invalid.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case '6' -> {

                    if (!currentSessionId.isBlank()) {
                        currentSessionId = "";
                        System.out.println("Logged out.");
                    } else {
                        System.out.println("You are not logged in.");
                    }
                }
                default -> {
                    StringFormatter.typeWrite("Invalid option entered.", 10);
                    break;
                }
            }
        }
    }

    public static void clrscr() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException | InterruptedException ignored) {
        }
    }

    public static void showLogo() throws FileNotFoundException {
        File ascii = new File(asciiPath);
        try (Scanner sc = new Scanner(ascii)) {
            while (sc.hasNextLine()) {
                String data = sc.nextLine();
                System.out.println(data);
            }
            System.out.flush();
        }

    }
}
