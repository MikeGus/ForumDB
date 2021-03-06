package forum.rowmappers;

import forum.models.ForumModel;
import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by MikeGus on 21.10.17
 */

public class RowMapperCollection {

    public static RowMapper<PostModel> readPost = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new PostModel(rs.getString("nickname"), df.format(ts.getTime()),
                rs.getString("slug"), rs.getInt("id"),
                rs.getBoolean("is_edited"), rs.getString("message"),
                rs.getInt("parent_id"), rs.getInt("thread_id"));
    };

    public static RowMapper<ThreadModel> readThread = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new ThreadModel(rs.getString("author"), df.format(ts.getTime()),
                rs.getString("forum"), rs.getInt("id"),
                rs.getString("message"), rs.getString("slug"),
                rs.getString("title"), rs.getInt("votes"));
    };

    public static RowMapper<UserModel> readUser = (rs, i) ->
            new UserModel(rs.getString("about"), rs.getString("email"),
                rs.getString("fullname"), rs.getString("nickname"));

    public static RowMapper<ForumModel> readForum = (rs, i) ->
        new ForumModel(rs.getInt("posts"), rs.getString("slug"),
                rs.getInt("threads"), rs.getString("title"),
                rs.getString("user"));
}
