package forum.Jdbc;

import forum.DAO.UserDAO;
import forum.models.UserModel;
import forum.models.UserUpdateModel;
import forum.queries.UserQueries;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


public class JdbcUser extends JdbcDaoSupport implements UserDAO {

    JdbcUser(JdbcTemplate template) {
        this.setJdbcTemplate(template);
    }

    public void create(final String nickname, final UserModel profile) {
        getJdbcTemplate().update(UserQueries.create(),
                new Object[] {profile.getAbout(), profile.getEmail(), profile.getFullname(), nickname});
    }

    public UserModel getByNickname(final String nickname) {
        return getJdbcTemplate().queryForObject(UserQueries.getByNickname(),
                new Object[] {nickname}, new BeanPropertyRowMapper<>(UserModel.class));
    }

    public UserModel getByNicknameOrEmail(final String nicknameOrEmail) {
        return getJdbcTemplate().queryForObject(UserQueries.getByNicknameOrEmail(),
                new Object[] {nicknameOrEmail, nicknameOrEmail}, new BeanPropertyRowMapper<>(UserModel.class));
    }

    public UserModel update(final String nickname, final UserUpdateModel profile) {
        getJdbcTemplate().update(UserQueries.update(),
                new Object[] {profile.getAbout(), profile.getEmail(), profile.getFullname(), nickname});

        return getByNickname(nickname);
    }

    public Integer status() {
        return getJdbcTemplate().queryForObject(UserQueries.status(), new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        getJdbcTemplate().execute(UserQueries.clear());
    }
}
