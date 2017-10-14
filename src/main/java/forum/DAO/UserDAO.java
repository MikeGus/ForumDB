package forum.DAO;

import forum.models.User;
import forum.models.UserUpdate;
import org.springframework.stereotype.Service;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface UserDAO {
    void create(final String nickname, final User profile);
    User getByNickname(final String nickname);
    User update(final String nickname, final UserUpdate profile);

    Integer status();
    void clear();
}
