package forum.dao;

import forum.models.PostModel;
import forum.models.PostFullModel;
import forum.models.PostUpdateModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
@Service
public interface PostDAO {
    void create(final String slug_or_id, final List<PostModel> posts);
    PostFullModel getByIdFull(final Integer id, String[] related);
    PostModel update(final Integer id, final PostUpdateModel post);

    Integer status();
    void clear();
}
