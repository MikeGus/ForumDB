package forum.Jdbc;

import forum.DAO.ThreadDAO;
import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.rowmappers.ThreadRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

@SuppressWarnings("all")
public class JdbcThread extends JdbcDaoSupport implements ThreadDAO {

    public void create(final String slug, final ThreadModel thread) {
        return;
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {

        String sql = "SELECT u.nickname, t.created, f.slug, t.id, t.message, t.slug, t.title, t.votes " +
                "FROM threads t JOIN users u ON (u.id = t.user_id) JOIN forums f ON (f.id = t.forum_id) ";
        StringBuilder builder = new StringBuilder(sql);

        if (slug_or_id.matches("//d+")) {
            builder.append("WHERE t.id = ?");
        }
        else {
            builder.append("WHERE t.slug = ?");
        }

        return (ThreadModel) getJdbcTemplate().queryForObject(builder.toString(),
                new Object[] {slug_or_id},
                new ThreadRowMapper());
    }

    public ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread) {
        ThreadModel threadDB = getBySlugOrId(slug_or_id);
        threadDB.setMessage(thread.getMessage());
        threadDB.setTitle(thread.getTitle());

        String sql = "UPDATE threads " +
                "SET title = ?, message = ? " +
                "WHERE id = ?";

        getJdbcTemplate().update(sql, new Object[] {threadDB.getTitle(), threadDB.getMessage(), threadDB.getId()});
        return threadDB;
    }

    public List<PostModel> getPosts(final String slug_or_id, final Integer limit, final Integer since, final String sort,
                                    final Boolean desc) {
        return null;
    }

    public ThreadModel vote(final String slug_or_id, final VoteModel vote) {
        return null;
    }

    public Integer status() {
        String sql = "SELECT COUNT(*) FROM threads";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        String sql = "DELETE FROM threads";
        getJdbcTemplate().execute(sql);
    }
}
