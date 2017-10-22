package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class UserQueries {

    public static String create = "INSERT INTO users (about, email, fullname, nickname) VALUES (?, ?, ?, ?)";

    public static String getByNickname = "SELECT * FROM users WHERE LOWER(nickname) = " +
            "LOWER(?)";

    public static String getByNicknameOrEmail = "SELECT * FROM users " +
            "WHERE (LOWER(nickname) = LOWER(?) OR LOWER(email) = LOWER(?))";

    public static String update = "UPDATE users SET about = ?, email = ?, fullname = ? WHERE LOWER(nickname) = " +
            "LOWER(?)";

    public static String status = "SELECT COUNT(*) FROM users";

    public static String clear = "DELETE FROM users";

    public static String getIdByNickname = "SELECT id FROM users WHERE LOWER(nickname) = LOWER(?)";
}
