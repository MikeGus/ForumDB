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
        StringBuilder builder = new StringBuilder("SELECT about, email, fullname, nickname ");
        builder.append("FROM users u JOIN posts p ON (u.id = p.user_id) ");
        builder.append("JOIN forums f ON (p.forum_id = f.id) ");
        builder.append("WHERE f.id = ? ");

        String nicknameCheck = ("AND LOWER(nickname) ");
        String sign = (desc == Boolean.TRUE ? "< " : "> ");
        String descExp = (desc == Boolean.TRUE ? "DESC " : "ASC ");
        String limitExp = (limit == null ? "" : "LIMIT ?");

        if (since != null) {
            builder.append(nicknameCheck).append(sign).append("LOWER(?) ");
        }

        builder.append("UNION ").append("SELECT about, email, fullname, nickname ");
        builder.append("FROM users us JOIN threads th ON (us.id = th.user_id) ");
        builder.append("JOIN forums fo ON (th.forum_id = fo.id) ");
        builder.append("WHERE fo.id = ? ");

        if (since != null) {
            builder.append(nicknameCheck).append(sign).append("LOWER(?) ");
        }

        builder.append("ORDER BY nickname ");
        builder.append(descExp);
        builder.append(limitExp);

        return builder.toString();
    }

    public static String getSlugById = "SELECT slug FROM forums WHERE id = ?";

    public static String getIdBySlug = "SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)";
}
