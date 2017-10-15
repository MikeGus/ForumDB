package forum.rowmappers;

import forum.models.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by MikeGus on 15.10.17
 */

public class UserRowMapper implements RowMapper {
    public UserModel mapRow(ResultSet rs, int i) throws SQLException {
        return new UserModel(rs.getString("u.about"), rs.getString("u.email"),
                rs.getString("u.fullname"), rs.getString("u.nickname"));
    }
}
