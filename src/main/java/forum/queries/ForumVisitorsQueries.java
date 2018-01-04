package forum.queries;

public class ForumVisitorsQueries {
    public static String addUserToForumVisitors = "INSERT INTO forum_visitors (user_id, forum_id) VALUES (?, ?) " +
            "ON CONFLICT (user_id, forum_id) DO NOTHING";
    public static String clear = "TRUNCATE forum_visitors CASCADE";
}