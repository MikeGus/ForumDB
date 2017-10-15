package forum.DAO;

import forum.models.ForumModel;
import forum.models.UserModel;
import forum.models.ThreadModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface ForumDAO {
    void create(final ForumModel forum);
    ForumModel getBySlug(final String slug);

    Integer status();
    void clear();

    List<ThreadModel> getThreads(final String slug, final Integer limit, final String since, final Boolean desc);
    List<UserModel> getUsers(final String slug, final Integer limit, final String since, final Boolean desc);
}
