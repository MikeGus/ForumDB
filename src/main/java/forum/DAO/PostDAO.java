package forum.DAO;

import forum.models.Post;
import forum.models.PostFull;
import forum.models.PostUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface PostDAO {
    void create(final String slug_or_id, final List<Post> posts);
    PostFull getByIdFull(final Integer id, String[] related);
    Post update(final Integer id, final PostUpdate post);

    Integer status();
    void clear();
}
