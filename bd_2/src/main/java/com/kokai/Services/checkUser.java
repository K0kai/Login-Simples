package com.kokai.Services;

import com.kokai.User.User;
import com.kokai.User.UserContainer;

public class checkUser {

    // We use ints instead of booleans to check for the results of the checkage.
    // 0 being no collision, 1 being collision detected, 2 being checkage error.
    public static int checkNameCollision(UserContainer userContainer, String id, String name) {
        int nameCollides = 2;
        if (userContainer != null) {
            switch (id.trim().charAt(0)) {
                case 'u' -> {
                    if (userContainer.getUsers() != null) {
                        for (User user : userContainer.getUsers()) {
                            if (user.getName().equals(name)) {
                                nameCollides = 1;
                                return nameCollides;
                            } else {
                                nameCollides = 0;
                            }

                        }
                    } else {
                        nameCollides = 0;
                        return nameCollides;
                    }

                }
                case 'a' -> {
                    if (userContainer.getAdmins() != null) {
                        for (User user : userContainer.getAdmins()) {
                            if (user.getName().equals(name)) {
                                nameCollides = 1;
                                return nameCollides;
                            } else {
                                nameCollides = 0;
                            }

                        }
                    } else {
                        nameCollides = 0;
                        return nameCollides;
                    }

                }
                default -> {
                    System.out.println("Invalid Id for checkage.");
                    return 2;
                }
            }
        } else {
            return 0;
        }
        return nameCollides;
    }

    public static int checkEmailCollision(UserContainer userContainer, String id, String email) {
        int emailCollides = 2;
        if (userContainer != null) {
            switch (id.trim().charAt(0)) {
                case 'u' -> {
                    if (userContainer.getUsers() != null) {
                        for (User user : userContainer.getUsers()) {
                            if (user.getEmail().equals(email)) {
                                emailCollides = 1;
                                return emailCollides;
                            } else {
                                emailCollides = 0;
                            }

                        }
                    } else {
                        emailCollides = 0;
                        return emailCollides;
                    }

                }
                case 'a' -> {
                    if (userContainer.getAdmins() != null) {
                        for (User user : userContainer.getAdmins()) {
                            if (user.getEmail().equals(email)) {
                                emailCollides = 1;
                                return emailCollides;
                            } else {
                                emailCollides = 0;
                            }

                        }
                    } else {
                        emailCollides = 0;
                        return emailCollides;
                    }

                }
                default -> {
                    System.out.println("Invalid Id for checkage.");
                    return 2;
                }
            }
        } else {
            return 0;
        }
        return emailCollides;
    }

    public static int checkIDCollision(UserContainer userContainer, String id) {
        int idCollides = 2;
        if (userContainer != null) {
            switch (id.trim().charAt(0)) {
                case 'u' -> {
                    if (userContainer.getUsers() != null) {
                        for (User user : userContainer.getUsers()) {
                            if (user.getId().equals(id)) {
                                idCollides = 1;
                                return idCollides;
                            } else {
                                idCollides = 0;
                            }

                        }
                    } else {
                        idCollides = 0;
                        return idCollides;
                    }

                }
                case 'a' -> {
                    if (userContainer.getAdmins() != null) {
                        for (User user : userContainer.getAdmins()) {
                            if (user.getId().equals(id)) {
                                idCollides = 1;
                                return idCollides;
                            } else {
                                idCollides = 0;
                            }

                        }
                    } else {
                        idCollides = 0;
                        return idCollides;
                    }

                }
                default -> {
                    System.out.println("Invalid Id for checkage.");
                    return 2;
                }

            }
        } else {
            idCollides = 0;
            return idCollides;
        }
        return idCollides;
    }

}
