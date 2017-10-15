package forum.rowmappers;

import forum.models.PostModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by MikeGus on 15.10.17
 */

public class PostRowMapper implements RowMapper {
    public PostModel mapRow(ResultSet rs, int i) throws SQLException {
        final Timestamp ts = rs.getTimestamp("p.created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm::ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new PostModel(rs.getString("u.nickname"), df.format(ts.getTime()),
                rs.getString("f.slug"), rs.getInt("p.id"),
                rs.getBoolean("p.is_edited"), rs.getString("p.message"),
                rs.getInt("p.parent"), rs.getInt("p.thread"));
    }
}
