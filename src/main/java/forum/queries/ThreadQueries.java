package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class ThreadQueries {

    private String author;
    private String created;
    private String forum;
    private Integer id;
    private String message;
    private String slug;
    private String title;
    private Integer votes;

    public static String create = "INSERT INTO threads (user_id, created, forum_id, message, slug, title) " +
        "VALUES ((SELECT id FROM users WHERE nickname = ?), COALESCE(?::TIMESTAMPTZ, CURRENT_TIMESTAMP), " +
            "(SELECT id FROM forums WHERE slug = ?), ?, ?, ?) RETURNING id";

    public static String getBySlugOrId(final String slug_or_id) {
        StringBuilder builder = new StringBuilder(
                "SELECT u.nickname AS author, t.created AS created, f.slug AS forum, t.id AS id, t.message AS message, " +
                        "t.slug AS slug, t.title AS title, t.votes AS votes " +
                "FROM threads t " +
                "JOIN users u ON (u.id = t.user_id) " +
                "JOIN forums f ON (f.id = t.forum_id) "
        );

        if (slug_or_id.matches("//d+")) {
            builder.append("WHERE t.id = ?");
        }
        else {
            builder.append("WHERE LOWER(t.slug) = LOWER(?)");
        }

        return builder.toString();
    }

    public static String getById = "SELECT u.nickname AS author, t.created , f.slug AS forum, t.id," +
            "t.message AS message, t.slug AS slug, t.title, t.votes FROM threads t JOIN users u ON (u.id = t.user_id) " +
            "JOIN forums f ON (f.id = t.forum_id) WHERE t.id = ?";

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
                    "FROM threads t"+
                    "JOIN forums f on (f.id = t.forum_id) " +
                    "WHERE t.id = ?";
        }
        else {
            return "SELECT f.id " +
                    "FROM threads t"+
                    "JOIN forums f on (f.id = t.forum_id) " +
                    "WHERE t.slug = ?";
        }
    }

    public static String updateThreadCount = "UPDATE forums SET threads = threads + ? WHERE id = ?";
}
