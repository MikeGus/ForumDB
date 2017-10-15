package forum.Jdbc;

import forum.DAO.ThreadDAO;
import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.queries.ThreadQueries;
import forum.rowmappers.ThreadRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcThread extends JdbcDaoSupport implements ThreadDAO {

    JdbcThread(JdbcTemplate template) {
        setJdbcTemplate(template);
    }

//    todo
    public void create(final String slug, final ThreadModel thread) {

        getJdbcTemplate().update(ThreadQueries.updateThreadCount(), new Object[]{1, thread.getId()});
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {
        return (ThreadModel) getJdbcTemplate().queryForObject(ThreadQueries.getBySlugOrId(slug_or_id),
                new Object[] {slug_or_id},
                new ThreadRowMapper());
    }

    public ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread) {
        ThreadModel threadDB = getBySlugOrId(slug_or_id);
        threadDB.setMessage(thread.getMessage());
        threadDB.setTitle(thread.getTitle());

        getJdbcTemplate().update(ThreadQueries.update(),
                new Object[] {threadDB.getTitle(), threadDB.getMessage(), threadDB.getId()});

        return threadDB;
    }

//    todo
    public List<PostModel> getPosts(final String slug_or_id, final Integer limit, final Integer since, final String sort,
                                    final Boolean desc) {
        return null;
    }

//    todo
    public ThreadModel vote(final String slug_or_id, final VoteModel vote) {
        return null;
    }

    public Integer status() {
        return getJdbcTemplate().queryForObject(ThreadQueries.status(), new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        getJdbcTemplate().execute(ThreadQueries.clear());
    }
}
