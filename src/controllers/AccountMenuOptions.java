package controllers;

import entities.Book;
import exceptions.NotExistenceChoice;
import services.BooksManager;

public enum AccountMenuOptions {
    ADD_BOOK, VIEW_ALL_BOOK, DELETE_BOOK, SEARCH_BOOK, LOG_OUT;

    public static AccountMenuOptions valueOf(int choice) {
        switch (choice) {
            case 1:
                return AccountMenuOptions.ADD_BOOK;
            case 2:
                return AccountMenuOptions.VIEW_ALL_BOOK;
            case 3:
                return AccountMenuOptions.DELETE_BOOK;
            case 4:
                return AccountMenuOptions.SEARCH_BOOK;
            case 5:
                return AccountMenuOptions.LOG_OUT;
            default:
                throw new NotExistenceChoice(choice);
        }
    }
}
