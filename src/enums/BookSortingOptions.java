package enums;

import exceptions.NotExistenceChoice;

public enum BookSortingOptions {
    BY_TITLE_ASC, BY_TITLE_DESC, BY_PUBLISHED_YEAR_ASC, BY_PUBLISHED_YEAR_DESC, GO_BACK;

    public static BookSortingOptions valueOf(int choice) {

        switch (choice) {
            case 1:
                return BookSortingOptions.BY_TITLE_ASC;
            case 2:
                return BookSortingOptions.BY_TITLE_DESC;
            case 3:
                return BookSortingOptions.BY_PUBLISHED_YEAR_ASC;
            case 4:
                return BookSortingOptions.BY_PUBLISHED_YEAR_DESC;
            case 5:
                return BookSortingOptions.GO_BACK;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
