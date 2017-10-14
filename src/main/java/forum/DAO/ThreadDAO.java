package forum.DAO;

import forum.models.Post;
import forum.models.ThreadUpdate;
import forum.models.Vote;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface ThreadDAO {
    void create(final String slug, final Thread thread);
    Thread getBySlugOrId(final String slug_or_id);
    Thread update(final String slug_or_id, final ThreadUpdate thread);
    List<Post> getPosts(final String slug_or_id, final Integer limit, final Integer since, final String sort,
                        final Boolean desc);
    Thread vote(final String slug_or_id, final Vote vote);

    Integer status();
    void clear();
}
