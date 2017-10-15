package forum.DAO;

import forum.models.UserModel;
import forum.models.UserUpdateModel;
import org.springframework.stereotype.Service;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface UserDAO {
    void create(final String nickname, final UserModel profile);
    UserModel getByNickname(final String nickname);
    UserModel update(final String nickname, final UserUpdateModel profile);

    Integer status();
    void clear();
}
