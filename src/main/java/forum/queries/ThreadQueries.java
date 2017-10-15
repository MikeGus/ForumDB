package forum.queries;

/**
 * Created by MikeGus on 15.10.17
 */

public class ThreadQueries {

//    todo
    public static String create() {

        return "";
    }

    public static String getBySlugOrId(final String slug_or_id) {
        StringBuilder builder = new StringBuilder(
                "SELECT u.nickname, t.created, f.slug, t.id, t.message, t.slug, t.title, t.votes " +
                "FROM threads t " +
                "JOIN users u ON (u.id = t.user_id) " +
                "JOIN forums f ON (f.id = t.forum_id) "
        );

        if (slug_or_id.matches("//d+")) {
            builder.append("WHERE t.id = ?");
        }
        else {
            builder.append("WHERE t.slug = ?");
        }

        return builder.toString();
    }

    public static String update() {
        return "UPDATE threads " +
                "SET title = ?, message = ? " +
                "WHERE id = ?";
    }

    public static String status() {
        return "SELECT COUNT(*) FROM threads";
    }

    public static String clear() {
        return "DELETE FROM threads";
    }

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

    public static String updateThreadCount() {
        return "UPDATE forums "+
                "SET threads = threads + ? "+
                "WHERE id = ?";
    }
}
