package enums;

import exceptions.NotExistenceChoice;

public enum BookFilteringMenu {
    BY_AUTHOR, BY_GENRE, BY_READ_BOOKS ,GO_BACK;

    public static BookFilteringMenu valueOf(int choice){

        switch (choice){
            case 1:
                return BookFilteringMenu.BY_AUTHOR;
            case 2:
                return BookFilteringMenu.BY_GENRE;
            case 3:
                return BookFilteringMenu.BY_READ_BOOKS;
            case 4:
                return BookFilteringMenu.GO_BACK;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
