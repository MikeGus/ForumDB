package forum.services;

import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.queries.ThreadQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import forum.rowmappers.RowMapperCollection;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

@Service
public class ThreadService {

    private JdbcTemplate template;

    public ThreadService(JdbcTemplate template) {
        this.template = template;
    }

//    todo
    public void create(final String slug, final ThreadModel thread) {
        template.update(ThreadQueries.updateThreadCount, 1, thread.getId());
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {
        return template.queryForObject(ThreadQueries.getBySlugOrId(slug_or_id),
                RowMapperCollection.readThread, slug_or_id);
    }

    public ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread) {
        ThreadModel threadDB = getBySlugOrId(slug_or_id);
        threadDB.setMessage(thread.getMessage());
        threadDB.setTitle(thread.getTitle());

        template.update(ThreadQueries.update(), threadDB.getTitle(), threadDB.getMessage(), threadDB.getId());

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
        return template.queryForObject(ThreadQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(ThreadQueries.clear);
    }
}
