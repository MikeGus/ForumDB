package forum.rowmappers;

import forum.models.ThreadModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by MikeGus on 15.10.17
 */

public class ThreadRowMapper implements RowMapper {
    public ThreadModel mapRow(ResultSet rs, int i) throws SQLException {
        final Timestamp ts = rs.getTimestamp("t.created");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm::ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        return new ThreadModel(rs.getString("u.nickname"), df.format(ts.getTime()),
                rs.getString("f.slug"), rs.getInt("t.id"),
                rs.getString("t.message"), rs.getString("t.slug"),
                rs.getString("t.title"), rs.getInt("t.votes"));
    }
}
