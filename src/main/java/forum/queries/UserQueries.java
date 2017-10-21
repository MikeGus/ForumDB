package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class UserQueries {

    public static String create = "INSERT INTO users (about, email, fullname, nickname) VALUES (?, ?, ?, ?)";

    public static String getByNickname = "SELECT about, email, fullname, nickname FROM users WHERE nickname = ?";

    public static String getByNicknameOrEmail = "SELECT about, email, fullname, nickname FROM users " +
            "WHERE (email = ? OR nickname = ?)";

    public static String update = "UPDATE users SET about = ?, email = ?, fullname = ? WHERE nickname = ?";

    public static String status = "SELECT COUNT(*) FROM users";

    public static String clear = "DELETE FROM users";
}
