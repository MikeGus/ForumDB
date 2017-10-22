package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

@SuppressWarnings("WeakerAccess")
public class ThreadQueries {

    public static String create = "INSERT INTO threads (user_id, created, forum_id, message, slug, title) " +
        "VALUES ((SELECT id FROM users WHERE nickname = ?), COALESCE(?::TIMESTAMPTZ, CURRENT_TIMESTAMP), " +
            "(SELECT id FROM forums WHERE LOWER(slug) = LOWER(?)), ?, ?, ?) RETURNING id";

    public static String getBySlugOrId(final String slug_or_id) {
        if (slug_or_id.matches("//d+")) {
           return getById;
        }

        return getBySlug;
    }

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

    public static String addVote = "INSERT INTO votes (user_id, thread_id, voice) VALUES ((SELECT id FROM users " +
            "WHERE LOWER(nickname) = LOWER(?)), ?, ?)";

    public static String updateVotes = "UPDATE threads SET votes = votes + ? WHERE id = ?";
}
