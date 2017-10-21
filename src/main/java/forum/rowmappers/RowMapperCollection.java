package forum.rowmappers;

import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class RowMapperCollection {

    public static RowMapper<PostModel> readPost = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("p.created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm::ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new PostModel(rs.getString("u.nickname"), df.format(ts.getTime()),
                rs.getString("f.slug"), rs.getInt("p.id"),
                rs.getBoolean("p.is_edited"), rs.getString("p.message"),
                rs.getInt("p.parent"), rs.getInt("p.thread"));
    };

    public static RowMapper<ThreadModel> readThread = (rs, i) -> {
        final Timestamp ts = rs.getTimestamp("t.created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm::ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new ThreadModel(rs.getString("u.nickname"), df.format(ts.getTime()),
                rs.getString("f.slug"), rs.getInt("t.id"),
                rs.getString("t.message"), rs.getString("t.slug"),
                rs.getString("t.title"), rs.getInt("t.votes"));
    };

    public static RowMapper<UserModel> readUser = (rs, i) -> {
        System.out.println("BOI:" + rs.getString(1));
        return new UserModel(rs.getString("about"), rs.getString("email"),
                rs.getString("fullname"), rs.getString("nickname"));
    };


}
