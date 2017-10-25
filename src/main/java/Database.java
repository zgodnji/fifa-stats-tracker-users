import java.util.ArrayList;
import java.util.List;


public class Database {
    private static List<User> users = new ArrayList<>();

    public static List<User> getUsers() {
        return users;
    }

    public static User getUser(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId))
                return user;
        }

        return null;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void deleteUser(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                break;
            }
        }
    }
}
