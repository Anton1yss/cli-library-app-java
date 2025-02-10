package controllers;

import exceptions.NotExistenceChoice;

public enum BookSortingMenuOptions {
    BY_TITLE_ASC, BY_TITLE_DESC, BY_PUBLISHED_YEAR_ASC, BY_PUBLISHED_YEAR_DESC, GO_BACK;

    public static BookSortingMenuOptions valueOf(int choice) {

        switch (choice) {
            case 1:
                return BookSortingMenuOptions.BY_TITLE_ASC;
            case 2:
                return BookSortingMenuOptions.BY_TITLE_DESC;
            case 3:
                return BookSortingMenuOptions.BY_PUBLISHED_YEAR_ASC;
            case 4:
                return BookSortingMenuOptions.BY_PUBLISHED_YEAR_DESC;
            case 5:
                return BookSortingMenuOptions.GO_BACK;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
