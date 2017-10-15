package forum.Jdbc;

import forum.DAO.UserDAO;
import forum.models.UserModel;
import forum.models.UserUpdateModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


@SuppressWarnings("all")
public class JdbcUser extends JdbcDaoSupport implements UserDAO {

    public void create(final String nickname, final UserModel profile) {
        String sql = "INSERT INTO users (about, email, fullname, nickname) " +
                "VALUES (?, ?, ?, ?)";
        getJdbcTemplate().update(sql, new Object[] {profile.getAbout(), profile.getEmail(), profile.getFullname(),
        nickname});
    }

    public UserModel getByNickname(final String nickname) {
        String sql = "SELECT about, email, fullname, nickname " +
                "FROM users " +
                "WHERE nickname = ?";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(UserModel.class));
    }

    public UserModel update(final String nickname, final UserUpdateModel profile) {
        String sql = "UPDATE users " +
                "SET about = ?, email = ?, fullname = ? " +
                "WHERE nickname = ?";
        getJdbcTemplate().update(sql, new Object[] {profile.getAbout(), profile.getEmail(), profile.getFullname(),
                nickname});

        return getByNickname(nickname);
    }

    public Integer status() {
        String sql = "SELECT COUNT(*) FROM users";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        String sql = "DELETE FROM users";
        getJdbcTemplate().execute(sql);
    }
}
