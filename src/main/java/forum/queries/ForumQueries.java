package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class ForumQueries {

    public static String create = "INSERT INTO forums (slug, title, user_id, user_nickname) " +
            "VALUES (?, ?, (SELECT id FROM users WHERE LOWER(nickname) = LOWER(?))," +
            "(SELECT nickname FROM users WHERE LOWER(nickname) = LOWER(?)))";

    public static String getBySlug = "SELECT f.posts, f.slug, f.threads, " +
            "f.title, user_nickname AS user FROM forums f WHERE LOWER(f.slug) = LOWER(?)";

    public static String status = "SELECT COUNT(*) FROM forums";

    public static String clear = "TRUNCATE forums CASCADE";

    public static String getThreads(final Integer limit, final String since, final Boolean desc) {
        StringBuilder builder = new StringBuilder("SELECT user_nickname AS author, created, forum_slug AS forum, ");
        builder.append("id, message, slug, title, votes ");
        builder.append("FROM threads WHERE LOWER(forum_slug) = LOWER(?) ");

        String sign = (desc == Boolean.TRUE ? "<= " : ">= ");

        if (since != null) {
            builder.append("AND created ").append(sign).append("?::TIMESTAMPTZ ");
        }

        builder.append("ORDER BY created ");

        if (desc == Boolean.TRUE) {
            builder.append("DESC ");
        }

        if (limit != null) {
            builder.append("LIMIT ? ");
        }

        return builder.toString();
    }

    public static String getUsers(final Integer limit, final String since, final Boolean desc) {
        String sign = (desc == Boolean.TRUE ? "< " : "> ");
        String descExp = (desc == Boolean.TRUE ? "DESC " : "ASC ");

        StringBuilder builder = new StringBuilder("SELECT about, email, fullname, nickname ");
        builder.append("FROM users u JOIN forum_visitors f ON (u.id = f.user_id) ");
        builder.append(" WHERE f.forum_id = ? ");
        if (since != null) {
            builder.append(" AND nickname ").append(sign).append("'").append(String.valueOf(since)).append("' ");
        }
        builder.append(" ORDER BY nickname ").append(descExp);
        if (limit != null) {
            builder.append("LIMIT ").append(String.valueOf(limit));
        }

        return builder.toString();
    }

    public static String getSlugById = "SELECT slug FROM forums WHERE id = ?";

    public static String getIdBySlug = "SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)";
}
