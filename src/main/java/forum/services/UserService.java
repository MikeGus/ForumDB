package forum.services;

import forum.models.UserModel;
import forum.models.UserUpdateModel;
import forum.queries.UserQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import static forum.rowmappers.RowMapperCollection.readUser;

@Service
public class UserService {

    private JdbcTemplate template;

    public UserService(JdbcTemplate template) {
        this.template = template;
    }

    public void create(final String nickname, final UserModel profile) {
        template.update(UserQueries.create, profile.getAbout(), profile.getEmail(), profile.getFullname(), nickname);
    }

    public UserModel getByNickname(final String nickname) {
        return template.queryForObject(UserQueries.getByNickname, readUser, nickname);
    }

    public UserModel getByNicknameOrEmail(final String nicknameOrEmail) {
        return template.queryForObject(UserQueries.getByNicknameOrEmail,
                readUser, nicknameOrEmail, nicknameOrEmail);
    }

    public UserModel update(final String nickname, final UserUpdateModel profile) {
        template.update(UserQueries.update, profile.getAbout(), profile.getEmail(),
                profile.getFullname(), nickname);

        return getByNickname(nickname);
    }

    public Integer status() {
        return template.queryForObject(UserQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(UserQueries.clear);
    }
}
