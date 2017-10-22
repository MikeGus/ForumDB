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

    private String author;
    private String created;
    private String forum;
    private Integer id;
    private Boolean isEdited;
    private String message;
    private Integer parent;
    private Integer thread;

    public static String getPostsFlat(final Integer limit, final Integer since, final Boolean desc ) {

        StringBuilder builder = new StringBuilder(
                "SELECT u.nickname, p.created, f.slug, p.id, p.is_edited, p.message, p.parent_id, p.thread_id " +
                "FROM users u " +
                "JOIN posts p ON (u.id = p.user_id) " +
                "JOIN forums f ON (f.id = p.forum_id) " +
                "WHERE p.thread_id = ? ");

        String order = " ";
        String sign = " > ";

        if (desc == Boolean.TRUE) {
            sign = " < ";
            order = " DESC ";
        }

        if (since != null) {

            builder.append(" AND p.id").append(sign).append("? ");
        }
        builder.append("ORDER BY p.created, p.id ").append(order);

        if (limit != null) {
            builder.append("LIMIT ?");
        }

        return builder.toString();
    }
}
