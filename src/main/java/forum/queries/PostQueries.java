package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class PostQueries {

    @Deprecated
    public static String create(final Integer numberOfPosts) {

        StringBuilder builder = new StringBuilder(
                "INSERT INTO posts (user_id, created, forum_id, id, message, parent_id, thread_id, path, root_id) VALUES "
        );

        for (Integer i = 0; i < numberOfPosts; ++i) {
            builder.append("( ?, ?::TIMESTAMPTZ, ?, ?, ?, ?, ?, array_append(?, ?::BIGINT), ?)");
            if (i != numberOfPosts - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    public static String createSingle = "INSERT INTO posts " +
            "(user_id, user_nickname, created, forum_id, forum_slug, id, message, parent_id, thread_id, path, root_id) " +
            "VALUES (?, (SELECT nickname FROM users WHERE id = ?), ?::TIMESTAMPTZ, ?," +
            "(SELECT slug FROM forums WHERE id = ?), ?, ?, ?, ?, array_append(?, ?::BIGINT), ?)";

    public static String update = "UPDATE posts SET message = ?, is_edited = TRUE WHERE id = ?";

    public static String getById =
            "SELECT user_nickname AS nickname, created, forum_slug AS slug, id, is_edited, message, parent_id, thread_id " +
                "FROM posts WHERE id = ?";

    public static String status = "SELECT COUNT(*) FROM posts";

    public static String clear = "TRUNCATE posts CASCADE";

    public static String updatePostCount =
                "UPDATE forums " +
                "SET posts = posts + ? " +
                "WHERE id = ?";

    public static String checkParentId = "SELECT id FROM posts WHERE id = ? AND thread_id = ?";

    public static String getPath = "SELECT path FROM posts WHERE id = ?";

    public static String getNextId = "SELECT nextval(pg_get_serial_sequence('posts', 'id'))";
}
