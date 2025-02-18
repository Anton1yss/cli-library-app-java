package enums;

import exceptions.NotExistenceChoice;

public enum BookEditMenuOptions {
    EDIT_TITLE, EDIT_AUTHOR, EDIT_GENRE, EDIT_PUBLISHING_YEAR, EDIT_READ_STATE, GO_BACK;

    public static BookEditMenuOptions valueOf(int choice){
        switch (choice){
            case 1:
                return BookEditMenuOptions.EDIT_TITLE;
            case 2:
                return BookEditMenuOptions.EDIT_AUTHOR;
            case 3:
                return BookEditMenuOptions.EDIT_GENRE;
            case 4:
                return BookEditMenuOptions.EDIT_PUBLISHING_YEAR;
            case 5:
                return BookEditMenuOptions.EDIT_READ_STATE;
            case 6:
                return BookEditMenuOptions.GO_BACK;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
