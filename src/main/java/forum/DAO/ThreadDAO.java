package forum.DAO;

import forum.models.PostModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.models.ThreadModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface ThreadDAO {
    void create(final String slug, final ThreadModel thread);
    ThreadModel getBySlugOrId(final String slug_or_id);
    ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread);
    List<PostModel> getPosts(final String slug_or_id, final Integer limit, final Integer since, final String sort,
                             final Boolean desc);
    ThreadModel vote(final String slug_or_id, final VoteModel vote);

    Integer status();
    void clear();
}
