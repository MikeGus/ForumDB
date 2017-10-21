package forum.jdbc;

import forum.dao.UserDAO;
import forum.models.UserModel;
import forum.models.UserUpdateModel;
import forum.queries.UserQueries;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


public class JdbcUser extends JdbcTemplate implements UserDAO {

    private DataSource dataSource;

    public JdbcUser(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(final String nickname, final UserModel profile) {
        this.update(UserQueries.create, profile.getAbout(), profile.getEmail(), profile.getFullname(), nickname);
    }

    public UserModel getByNickname(final String nickname) {
        return this.queryForObject(UserQueries.getByNickname, new BeanPropertyRowMapper<>(UserModel.class),
                nickname);
    }

    public UserModel getByNicknameOrEmail(final String nicknameOrEmail) {
        return this.queryForObject(UserQueries.getByNicknameOrEmail,
                new BeanPropertyRowMapper<>(UserModel.class), nicknameOrEmail, nicknameOrEmail);
    }

    public UserModel update(final String nickname, final UserUpdateModel profile) {
        this.update(UserQueries.update, profile.getAbout(), profile.getEmail(),
                profile.getFullname(), nickname);

        return getByNickname(nickname);
    }

    public Integer status() {
        return this.queryForObject(UserQueries.status, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        this.execute(UserQueries.clear);
    }
}
