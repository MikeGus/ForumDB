package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class ForumQueries {

    public static String create() {
        return "INSERT INTO forums (slug, title, user_id) " +
                "VALUES (?, ?, (SELECT id FROM users WHERE nickname = ?))";
    }

    public static String getBySlug() {
        return "SELECT (*) FROM forums WHERE slug = ?";
    }

    public static String status() {
        return "SELECT COUNT(*) FROM forums";
    }

    public static String clear() {
        return "DELETE FROM forums";
    }

    public static String getThreads(final Integer limit, final String since, final Boolean desc) {
        StringBuilder builder = new StringBuilder(
                        "SELECT u.nickname, t.created, f.slug, t.id, t.message, t.slug, t.title, t.votes " +
                        "FROM threads t " +
                        "JOIN users u ON (t.user_id = u.id) " +
                        "JOIN forums f ON (f.id == t.forum_id) " +
                        "WHERE f.slug = ? "
        );

        if (since != null) {
            if (desc == Boolean.TRUE) {
                builder.append("AND t.created <= ? ");
            }
            else{
                builder.append("AND t.created >= ? ");
            }
        }

        builder.append("ORDER BY t.created ");
        if (desc == Boolean.TRUE) {
            builder.append("DESC ");
        }

        if (limit != null) {
            builder.append("LIMIT ? ");
        }

        return builder.toString();
    }

    public static String getUsers(final Integer limit, final String since, final Boolean desc) {
        StringBuilder builder = new StringBuilder(
                "SELECT u.about, u.email, u.fullname, u.nickname " +
                "FROM users u " +
                "JOIN forums f ON (t.user_id = u.id) " +
                "WHERE f.slug = ? "
        );

        if (since != null) {
            builder.append("AND u.nickname >= ? ");
        }

        builder.append("ORDER BY u.nickname ");
        if (desc == Boolean.TRUE) {
            builder.append("DESC ");
        }

        if (limit != null) {
            builder.append("LIMIT ? ");
        }

        return builder.toString();
    }
}
