package forum.jdbc;

import forum.dao.PostDAO;
import forum.models.PostModel;
import forum.models.PostFullModel;
import forum.models.PostUpdateModel;
import forum.queries.PostQueries;
import forum.queries.ThreadQueries;
import forum.rowmappers.RowMapperCollection;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcPost extends JdbcTemplate implements PostDAO {

    private DataSource dataSource;

    public JdbcPost(DataSource dataSource) {
        this.dataSource = dataSource;
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
        this.update(PostQueries.create(slug_or_id, posts.size()), params.toArray());

        Integer threadId = this.queryForObject(ThreadQueries.getForumIdByThreadSlugOrId(slug_or_id),
                new BeanPropertyRowMapper<>(Integer.class), slug_or_id);

        this.update(PostQueries.updatePostCount, new BeanPropertyRowMapper<>(Integer.class),
                posts.size(), threadId);
    }

//    todo
    public PostFullModel getByIdFull(final Integer id, String[] related) {
       return null;
    }

    public PostModel update(final Integer id, final PostUpdateModel post) {
        this.update(PostQueries.update, post.getMessage(), id);
        return this.queryForObject(PostQueries.getById, RowMapperCollection.readPost, id);
    }

    public Integer status() {
        return this.queryForObject(PostQueries.status, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        this.execute(PostQueries.clear);
    }
}
