package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class ForumQueries {

    public static String create = "INSERT INTO forums (slug, title, user_id) " +
            "VALUES (?, ?, (SELECT id FROM users WHERE LOWER(nickname) = LOWER(?)))";

    public static String getBySlug = "SELECT f.posts as posts, f.slug as slug, f.threads as threads, " +
            "f.title as title, u.nickname AS user FROM forums f " +
            "JOIN users u ON(f.user_id = u.id) WHERE LOWER(f.slug) = LOWER(?)";

    public static String status = "SELECT COUNT(*) FROM forums";

    public static String clear = "DELETE FROM forums";

    public static String getThreads(final Integer limit, final String since, final Boolean desc) {
        StringBuilder builder = new StringBuilder(
                        "SELECT u.nickname AS author, t.created, f.slug AS forum, t.id, t.message, t.slug, t.title, t.votes " +
                        "FROM threads t " +
                        "JOIN users u ON (t.user_id = u.id) " +
                        "JOIN forums f ON (f.id = t.forum_id) " +
                        "WHERE LOWER(f.slug) = LOWER(?) "
        );

        if (since != null) {
            if (desc == Boolean.TRUE) {
                builder.append("AND t.created <= ?::TIMESTAMPTZ ");
            }
            else{
                builder.append("AND t.created >= ?::TIMESTAMPTZ ");
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
