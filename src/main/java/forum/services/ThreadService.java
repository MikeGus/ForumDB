package forum.services;

import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
import forum.queries.ForumQueries;
import forum.queries.ThreadQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static forum.rowmappers.RowMapperCollection.readThread;

/**
 * Created by MikeGus on 15.10.17
 */

@Transactional
@Service
public class ThreadService {

    private JdbcTemplate template;

    public ThreadService(JdbcTemplate template) {
        this.template = template;
    }

    public ThreadModel create(final String slug, final ThreadModel thread) {
        Integer id = template.queryForObject(ThreadQueries.create, Integer.class,
                thread.getAuthor(),thread.getCreated(), slug, thread.getMessage(), thread.getSlug(), thread.getTitle());
        template.update(ThreadQueries.updateThreadCount, 1, thread.getForum());
        return template.queryForObject(ThreadQueries.getById, readThread, id);
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {
        return template.queryForObject(ThreadQueries.getBySlugOrId(slug_or_id),
                readThread, slug_or_id);
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

    public ThreadModel vote(final String slug_or_id, final VoteModel vote) {

        ThreadModel thread = getBySlugOrId(slug_or_id);

        template.update(ThreadQueries.addVote, vote.getNickname(), thread.getId(), vote.getVoice());
        template.update(ThreadQueries.updateVotes, vote.getVoice(), thread.getId());
        thread.setVotes(thread.getVotes() + vote.getVoice());

        return thread;
    }

    public Integer status() {
        return template.queryForObject(ThreadQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(ThreadQueries.clear);
    }
}
