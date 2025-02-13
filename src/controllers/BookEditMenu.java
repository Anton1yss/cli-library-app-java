package controllers;

import exceptions.NotExistenceChoice;

public enum BookEditMenu {
    EDIT_TITLE, EDIT_AUTHOR, EDIT_GENRE, EDIT_PUBLISHING_YEAR, EDIT_READ_STATE, GO_BACK;

    public static BookEditMenu valueOf(int choice){
        switch (choice){
            case 1:
                return BookEditMenu.EDIT_TITLE;
            case 2:
                return BookEditMenu.EDIT_AUTHOR;
            case 3:
                return BookEditMenu.EDIT_GENRE;
            case 4:
                return BookEditMenu.EDIT_PUBLISHING_YEAR;
            case 5:
                return BookEditMenu.EDIT_READ_STATE;
            case 6:
                return BookEditMenu.GO_BACK;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
