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
        final Timestamp ts = rs.getTimestamp("p.created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new PostModel(rs.getString("u.nickname"), df.format(ts.getTime()),
                rs.getString("f.slug"), rs.getInt("p.id"),
                rs.getBoolean("p.is_edited"), rs.getString("p.message"),
                rs.getInt("p.parent"), rs.getInt("p.thread"));
    };

    public static RowMapper<ThreadModel> readThread = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        ThreadModel result = new ThreadModel(rs.getString("author"), df.format(ts.getTime()),
                rs.getString("forum"), rs.getInt("id"),
                rs.getString("message"), rs.getString("slug"),
                rs.getString("title"), rs.getInt("votes"));
        return result;
    };

    public static RowMapper<UserModel> readUser = (rs, i) ->
            new UserModel(rs.getString("about"), rs.getString("email"),
                rs.getString("fullname"), rs.getString("nickname"));

    public static RowMapper<ForumModel> readForum = (rs, i) ->
        new ForumModel(rs.getInt("posts"), rs.getString("slug"),
                rs.getInt("threads"), rs.getString("title"),
                rs.getString("user"));

    public static RowMapper<PostModel> readPostData = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new PostModel(null, df.format(rs.getTimestamp("created")), null,
                rs.getInt("id"), Boolean.FALSE, null, null, null);
    };
}
