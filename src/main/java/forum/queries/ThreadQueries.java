package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

@SuppressWarnings("WeakerAccess")
public class ThreadQueries {

    public static String create = "INSERT INTO threads (user_id, created, forum_id, message, slug, title) " +
        "VALUES ((SELECT id FROM users WHERE nickname = ?), COALESCE(?::TIMESTAMPTZ, CURRENT_TIMESTAMP), " +
            "(SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)), ?, ?, ?) RETURNING id";


    public static String getById = "SELECT u.nickname AS author, t.created , f.slug AS forum, t.id, " +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t JOIN users u ON (u.id = t.user_id) " +
            "JOIN forums f ON (f.id = t.forum_id) WHERE t.id = ?";

    public static String getBySlug = "SELECT u.nickname AS author, t.created , f.slug AS forum, t.id," +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t JOIN users u ON (u.id = t.user_id) " +
            "JOIN forums f ON (f.id = t.forum_id) WHERE LOWER(t.slug) = LOWER(?)";

    public static String update() {
        return "UPDATE threads " +
                "SET title = ?, message = ? " +
                "WHERE id = ?";
    }

    public static String status = "SELECT COUNT(*) FROM threads";

    public static String clear = "DELETE FROM threads";

    public static String getForumIdByThreadSlugOrId(final String slug_or_id) {
        if (slug_or_id.matches("\\d+")) {
            return "SELECT f.id " +
                    "FROM threads t "+
                    "JOIN forums f on (f.id = t.forum_id) " +
                    "WHERE t.id = ?::INTEGER";
        }
        else {
            return "SELECT f.id " +
                    "FROM threads t "+
                    "JOIN forums f ON (f.id = t.forum_id) " +
                    "WHERE LOWER(t.slug) = LOWER(?)";
        }
    }

    public static String getThreadIdBySlug = "SELECT id FROM threads WHERE LOWER(slug) = LOWER(?)";

    public static String updateThreadCount = "UPDATE forums SET threads = threads + ? WHERE LOWER(slug) = LOWER(?)";

    public static String addVote = "INSERT INTO votes (user_id, thread_id, voice) VALUES (?, ?, ?)";

    public static String updateVotes = "UPDATE threads SET votes = votes + ? WHERE id = ?";

    public static String checkPreviousVote = "SELECT voice FROM votes WHERE user_id = ? AND thread_id = ?";

    public static String updateVote = "UPDATE votes SET voice = ? WHERE user_id = ? AND thread_id = ?";

    public static String checkThreadPresence = "SELECT id FROM threads WHERE id = ?";

    public static String getPostsFlat(final Integer limit, final Integer since, final Boolean desc ) {

        StringBuilder builder = new StringBuilder("SELECT u.nickname, p.created, f.slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM users u JOIN posts p ON (u.id = p.user_id) ");
        builder.append("JOIN forums f ON (f.id = p.forum_id) ");
        builder.append("WHERE p.thread_id = ? ");

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        if (since != null) {
            builder.append(" AND p.id").append(sign).append("? ");
        }
        builder.append("ORDER BY p.created, p.id ").append(order);

        if (limit != null) {
            builder.append("LIMIT ?");
        }

        return builder.toString();
    }

    public static String getPostsTree(final Integer limit, final Integer since, final Boolean desc ) {

        StringBuilder builder = new StringBuilder("SELECT u.nickname, p.created, f.slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM users u JOIN posts p ON (u.id = p.user_id) ");
        builder.append("JOIN forums f ON (f.id = p.forum_id) ");
        builder.append("WHERE p.thread_id = ? ");

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        if (since != null) {
            builder.append(" AND p.id ").append(sign).append("? ");
        }
        builder.append("ORDER BY p.path ").append(order);

        if (limit != null) {
            builder.append("LIMIT ?");
        }

        return builder.toString();
    }

    public static String getPostsParentTree(final Integer limit, final Integer since, final Boolean desc ) {

        StringBuilder builder = new StringBuilder("SELECT u.nickname, p.created, f.slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM users u JOIN posts p ON (u.id = p.user_id) ");
        builder.append("JOIN forums f ON (f.id = p.forum_id) ");
        builder.append("WHERE p.thread_id = ? AND p.id IN (SELECT id FROM posts WHERE parent_id=0 ORDER BY id ");
        builder.append(" OR )p.path[1] IN (SELECT id FROM posts WHERE parent_id=0 ORDER BY id ");
        if (limit != null) {
            builder.append(" LIMIT ?");
        }
        builder.append(") ");

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        if (since != null) {
            builder.append(" AND p.id").append(sign).append("? ");
        }
        builder.append("ORDER BY array_append(p.path, p.id) ").append(order);


        return builder.toString();
    }
}
