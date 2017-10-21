package forum.jdbc;

import forum.dao.ThreadDAO;
import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.queries.ThreadQueries;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import forum.rowmappers.RowMapperCollection;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcThread extends JdbcTemplate implements ThreadDAO {

    private DataSource dataSource;

    public JdbcThread(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    todo
    public void create(final String slug, final ThreadModel thread) {
        this.update(ThreadQueries.updateThreadCount, 1, thread.getId());
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {
        return this.queryForObject(ThreadQueries.getBySlugOrId(slug_or_id),
                RowMapperCollection.readThread, slug_or_id);
    }

    public ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread) {
        ThreadModel threadDB = getBySlugOrId(slug_or_id);
        threadDB.setMessage(thread.getMessage());
        threadDB.setTitle(thread.getTitle());

        this.update(ThreadQueries.update(), threadDB.getTitle(), threadDB.getMessage(), threadDB.getId());

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
        return this.queryForObject(ThreadQueries.status, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        this.execute(ThreadQueries.clear);
    }
}
