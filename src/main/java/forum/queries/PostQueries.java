package forum.queries;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by MikeGus on 15.10.17
 */

public class PostQueries {

    public static String create(final String slug_or_id, final Integer numberOfPosts) {

        String slugOrIdStr = slug_or_id.matches("\\d+") ? "?, " : "(SELECT id FROM forums WHERE slug = ?, ";

        StringBuilder builder = new StringBuilder(
                "INSERT INTO posts (user_id, created, forum_id, id, is_edited, message, parent_id, thread_id " +
                "VALUES "
        );

        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (Integer i = 0; i < numberOfPosts; ++i) {
            builder.append("((SELECT id FROM users WHERE nickname = ?), ?, ");
            builder.append(slugOrIdStr);
            builder.append("?, ?, ?, ?, ?)");
            if (i != numberOfPosts - 1) {
                builder.append(", ");
            }
            else {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    public static String update() {
        return "UPDATE posts " +
                "SET message = ?, is_edited = TRUE " +
                "WHERE id = ?";
    }

    public static String getById() {
        return "SELECT u.nickname, p.created, f.slug, p.id, p.is_edited, p.message, p.parent, p.thread " +
                "FROM posts p " +
                "JOIN users u ON (p.user_id = u.id) " +
                "JOIN forums f ON (p.forum_id = f.id) " +
                "WHERE p.id = ?";
    }

    public static String status() {
        return "SELECT COUNT(*) FROM posts";
    }

    public static String clear() {
        return "DELETE FROM posts";
    }
}
