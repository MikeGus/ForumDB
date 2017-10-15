package forum.Jdbc;

import forum.DAO.PostDAO;
import forum.models.PostModel;
import forum.models.PostFullModel;
import forum.models.PostUpdateModel;
import forum.queries.ForumQueries;
import forum.queries.PostQueries;
import forum.queries.ThreadQueries;
import forum.rowmappers.PostRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcPost extends JdbcDaoSupport implements PostDAO {

    JdbcPost(JdbcTemplate template) {
        setJdbcTemplate(template);
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
        getJdbcTemplate().update(PostQueries.create(slug_or_id, posts.size()), params.toArray());

        Integer threadId = getJdbcTemplate().queryForObject(ThreadQueries.getForumIdByThreadSlugOrId(slug_or_id),
                new Object[] {slug_or_id}, new BeanPropertyRowMapper<>(Integer.class));
        getJdbcTemplate().update(PostQueries.updatePostCount(),
                new Object[] {posts.size(), threadId}, new BeanPropertyRowMapper<>(Integer.class));
    }

//    todo
    public PostFullModel getByIdFull(final Integer id, String[] related) {
       return null;
    }

    public PostModel update(final Integer id, final PostUpdateModel post) {
        getJdbcTemplate().update(PostQueries.update(), new Object[]{post.getMessage(), id});
        return (PostModel) getJdbcTemplate().queryForObject(PostQueries.getById(),
                new Object[] {id},
                new PostRowMapper());
    }

    public Integer status() {
        return getJdbcTemplate().queryForObject(PostQueries.status(), new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        getJdbcTemplate().execute(PostQueries.clear());
    }
}
