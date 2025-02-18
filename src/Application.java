import controllers.MenuManager;
import services.UsersManager;

public class Application {
    public static void main(String[] args) {
        UsersManager usersManager = new UsersManager();
        MenuManager menuManager = new MenuManager();

        usersManager.loadUsersFromStorage();
        menuManager.mainMenu();
    }
}