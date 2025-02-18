package enums;

import exceptions.NotExistenceChoice;

public enum MainMenuOptions {
    REGISTER_USER, LOGIN_USER, GET_ALL_USERS, EXIT;

    public static MainMenuOptions valueOf(int choice) {
        switch (choice) {
            case 1:
                return REGISTER_USER;
            case 2:
                return LOGIN_USER;
            case 3:
                return GET_ALL_USERS;
            case 4:
                return EXIT;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
