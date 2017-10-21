package forum.services;

import forum.models.PostModel;
import forum.models.PostFullModel;
import forum.models.PostUpdateModel;
import forum.queries.PostQueries;
import forum.queries.ThreadQueries;
import forum.rowmappers.RowMapperCollection;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

@Service
public class PostService {

    private JdbcTemplate template;

    public PostService(JdbcTemplate template) {
        this.template = template;
    }

    public void create(final String slug_or_id, final List<PostModel> posts) {

        List<Object> params = new ArrayList<>();
        for (PostModel post : posts) {
            params.add(post.getAuthor());
            params.add(post.getCreated());
            params.add(slug_or_id);
            params.add(post.getId());
            params.add(post.getIsEdited());
            params.add(post.getMessage());
            params.add(post.getParent());
            params.add(post.getThread());
        }
        template.update(PostQueries.create(slug_or_id, posts.size()), params.toArray());

        Integer threadId = template.queryForObject(ThreadQueries.getForumIdByThreadSlugOrId(slug_or_id),
                new BeanPropertyRowMapper<>(Integer.class), slug_or_id);

        template.update(PostQueries.updatePostCount, new BeanPropertyRowMapper<>(Integer.class),
                posts.size(), threadId);
    }

//    todo
    public PostFullModel getByIdFull(final Integer id, String[] related) {
       return null;
    }

    public PostModel update(final Integer id, final PostUpdateModel post) {
        template.update(PostQueries.update, post.getMessage(), id);
        return template.queryForObject(PostQueries.getById, RowMapperCollection.readPost, id);
    }

    public Integer status() {
        return template.queryForObject(PostQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(PostQueries.clear);
    }
}
