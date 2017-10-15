package forum.Jdbc;

import forum.DAO.PostDAO;
import forum.models.PostModel;
import forum.models.PostFullModel;
import forum.models.PostUpdateModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by MikeGus on 15.10.17
 */

@SuppressWarnings("unused")
public class JdbcPost extends JdbcDaoSupport implements PostDAO {

    public void create(final String slug_or_id, final List<PostModel> posts) {
        String sql = "INSERT INTO posts (user_id, created, forum_id, id, is_edited, message, parent_id," +
                " thread_id) VALUES";
        StringBuilder builder = new StringBuilder(sql);

        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        List<Object> params = new ArrayList<>();

        for (PostModel post : posts) {
            builder.append(" ((SELECT id FROM users WHERE nickname = ?), ?,");
            params.add(post.getAuthor());

            params.add(post.getCreated());
            if (slug_or_id.matches("\\d+")) {
                builder.append("?,");
            }
            else {
                builder.append("(SELECT id from forums WHERE slug = ?),");
            }
            builder.append(" ?, ?, ?, ?, ?)");
        }

        getJdbcTemplate().update(builder.toString());
    }

    public PostFullModel getByIdFull(final Integer id, String[] related) {
       return null;
    }

    public PostModel update(final Integer id, final PostUpdateModel post) {
        String sql = "UPDATE posts " +
                "SET message = ?, is_edited = TRUE" +
                "WHERE id = ?";
        getJdbcTemplate().update(sql, new Object[] {post.getMessage(), id});
        return null;
    }

    public Integer status() {
        String sql = "SELECT COUNT(*) FROM posts";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        String sql = "DELETE FROM posts";
        getJdbcTemplate().execute(sql);
    }
}
