package forum.DAO;

import forum.models.Forum;
import forum.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface ForumDAO {
    void create(final Forum forum);
    Forum getBySlug(final String slug);

    Integer status();
    void clear();

    List<Thread> getThreads(final String slug, final Integer limit, final String since, final Boolean desc);
    List<User> getUsers(final String slug, final Integer limit, final String since, final Boolean desc);
}
