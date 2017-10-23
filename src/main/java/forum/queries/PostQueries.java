package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class PostQueries {

    public static String create(final Integer numberOfPosts) {

        StringBuilder builder = new StringBuilder(
                "INSERT INTO posts (user_id, created, forum_id, id, message, parent_id, thread_id, path) VALUES "
        );

        for (Integer i = 0; i < numberOfPosts; ++i) {
            builder.append("( ?, ?::TIMESTAMPTZ, ?, ?, ?, ?, ?, array_append(?, ?::BIGINT))");
            if (i != numberOfPosts - 1) {
                builder.append(", ");
            }
        }

        builder.append(" RETURNING created, id");
        return builder.toString();
    }

    public static String update = "UPDATE posts SET message = ?, is_edited = TRUE WHERE id = ?";

    public static String getById =
            "SELECT u.nickname, p.created, f.slug, p.id, p.is_edited, p.message, p.parent_id, p.thread_id " +
                "FROM posts p " +
                "JOIN users u ON (p.user_id = u.id) " +
                "JOIN forums f ON (p.forum_id = f.id) " +
                "WHERE p.id = ?";

    public static String status = "SELECT COUNT(*) FROM posts";

    public static String clear = "DELETE FROM posts";

    public static String updatePostCount =
                "UPDATE forums " +
                "SET posts = posts + ? " +
                "WHERE id = ?";

    public static String checkParentId = "SELECT id FROM posts WHERE id = ? AND thread_id = ?";

    public static String getPath = "SELECT path FROM posts WHERE id = ?";

    public static String getNextId = "SELECT nextval(pg_get_serial_sequence('posts', 'id'))";
}
