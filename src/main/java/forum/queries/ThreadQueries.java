package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

@SuppressWarnings("WeakerAccess")
public class ThreadQueries {

    public static String create = "INSERT INTO threads (user_id, user_nickname, created, forum_id, forum_slug, message, slug, title) " +
        "VALUES ((SELECT id FROM users WHERE nickname = ?), ?, COALESCE(?::TIMESTAMPTZ, CURRENT_TIMESTAMP), " +
            "(SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)), (SELECT slug FROM forums WHERE LOWER(slug) = LOWER(?)), ?, ?, ?) RETURNING id";


    public static String getById = "SELECT t.user_nickname AS author, t.created , forum_slug AS forum, t.id, " +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t WHERE t.id = ?";

    public static String getBySlug = "SELECT t.user_nickname AS author, t.created , forum_slug AS forum, t.id," +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t WHERE LOWER(t.slug) = LOWER(?)";

    public static String update() {
        return "UPDATE threads " +
                "SET title = ?, message = ? " +
                "WHERE id = ?";
    }

    public static String status = "SELECT COUNT(*) FROM threads";

    public static String clear = "TRUNCATE threads CASCADE";

    public static String getForumIdByThreadSlugOrId(final String slug_or_id) {
        if (slug_or_id.matches("\\d+")) {
            return "SELECT f.id " +
                    "FROM threads t "+
                    "JOIN forums f ON (f.id = t.forum_id) " +
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

        StringBuilder builder = new StringBuilder("SELECT p.user_nickname AS nickname, p.created, p.forum_slug AS slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM posts p ");
        builder.append("WHERE p.thread_id = ? ");

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        if (since != null) {
            builder.append(" AND p.id").append(sign).append("? ");
        }
        builder.append("ORDER BY p.id ").append(order);

        if (limit != null) {
            builder.append("LIMIT ?");
        }

        return builder.toString();
    }

    public static String getPostsTree(final Integer limit, final Integer since, final Boolean desc ) {

        StringBuilder builder = new StringBuilder("SELECT p.user_nickname AS nickname, p.created, p.forum_slug AS slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM posts p ");
        builder.append("WHERE p.thread_id = ? ");

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        if (since != null) {
            builder.append(" AND p.path ").append(sign).append("(SELECT path FROM posts WHERE id = ?) ");
        }
        builder.append("ORDER BY p.path ").append(order);

        if (limit != null) {
            builder.append("LIMIT ?");
        }

        return builder.toString();
    }

    public static String getPostsParentTree(final Integer limit, final Integer since, final Boolean desc ) {

        String order = (desc == Boolean.TRUE ? " DESC " : " ASC ");
        String sign = (desc == Boolean.TRUE ? " < " : " > ");

        StringBuilder builder = new StringBuilder("SELECT p.user_nickname AS nickname, p.created, p.forum_slug AS slug, p.id, p.is_edited,");
        builder.append(" p.message, p.parent_id, p.thread_id ");
        builder.append("FROM posts p ");
        builder.append("WHERE p.root_id IN (SELECT id FROM posts WHERE thread_id = ? AND parent_id=0 ");

        if (since != null) {
            builder.append(" AND path ").append(sign).append("(SELECT path FROM posts WHERE id = ?) ");
        }
        builder.append("ORDER BY id ").append(order);

        if (limit != null) {
            builder.append(" LIMIT ?");
        }
        builder.append(") ");


        builder.append("ORDER BY p.path ").append(order);


        return builder.toString();
    }
}
