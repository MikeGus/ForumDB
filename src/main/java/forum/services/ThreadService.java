package forum.services;

import forum.models.*;
import forum.queries.*;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static forum.rowmappers.RowMapperCollection.readPost;
import static forum.rowmappers.RowMapperCollection.readThread;

/**
 * Created by MikeGus on 15.10.17
 */

@Service
public class ThreadService {

    private JdbcTemplate template;

    public ThreadService(JdbcTemplate template) {
        this.template = template;
    }

    public ThreadModel create(final String slug, final ThreadModel thread) {
        Integer id = template.queryForObject(ThreadQueries.create, Integer.class,
                thread.getAuthor(), thread.getAuthor(), thread.getCreated(), slug, slug, thread.getMessage(), thread.getSlug(), thread.getTitle());
        template.update(ThreadQueries.updateThreadCount, 1, thread.getForum());
        Integer userId = template.queryForObject(UserQueries.getIdByNickname, Integer.class, thread.getAuthor());
        Integer forumId = template.queryForObject(ForumQueries.getIdBySlug, Integer.class, slug);
        template.update(ForumVisitorsQueries.addUserToForumVisitors, userId, forumId);
        return template.queryForObject(ThreadQueries.getById, readThread, id);
    }

    public ThreadModel getBySlugOrId(final String slug_or_id) {
        if (slug_or_id.matches("\\d+")) {
            return template.queryForObject(ThreadQueries.getById,
                    readThread, Integer.valueOf(slug_or_id));
        }
        return template.queryForObject(ThreadQueries.getBySlug,
                readThread, slug_or_id);
    }

    public ThreadModel update(final String slug_or_id, final ThreadUpdateModel thread) {

        ThreadModel threadDB = getBySlugOrId(slug_or_id);

        Integer notNullFields = 0;

        if (thread.getMessage() != null) {
            notNullFields++;
            threadDB.setMessage(thread.getMessage());
        }
        if (thread.getTitle() != null) {
            notNullFields++;
            threadDB.setTitle(thread.getTitle());
        }

        if (notNullFields > 0) {
            template.update(ThreadQueries.update(), threadDB.getTitle(), threadDB.getMessage(), threadDB.getId());
        }

        return threadDB;
    }

    public List<PostModel> getPosts(final String slug_or_id, final Integer limit, final Integer since, final String sort,
                                    final Boolean desc) {

        ThreadModel thread = getBySlugOrId(slug_or_id);


        List<Object> arguments = new ArrayList<>();
        arguments.add(thread.getId());
        if (since != null) {
            arguments.add(since);
        }
        if (limit != null) {
            arguments.add(limit);
        }

        if (sort == null) {
            return template.query(ThreadQueries.getPostsFlat(limit, since, desc), arguments.toArray(), readPost);
        }

        switch (sort) {
            case "flat" :
                return template.query(ThreadQueries.getPostsFlat(limit, since, desc), arguments.toArray(), readPost);
            case "tree" :
                return template.query(ThreadQueries.getPostsTree(limit,since,desc), arguments.toArray(), readPost);
            case "parent_tree" :
                return template.query(ThreadQueries.getPostsParentTree(limit, since, desc), arguments.toArray(), readPost);
            default:
                break;
        }
        return template.query(ThreadQueries.getPostsFlat(limit, since, desc), arguments.toArray(), readPost);
    }

    @SuppressWarnings("ConstantConditions")
    public ThreadModel vote(final String slug_or_id, final VoteModel vote) {

        ThreadModel thread = getBySlugOrId(slug_or_id);
        Integer user_id = template.queryForObject(UserQueries.getIdByNickname, Integer.class, vote.getNickname());

        try {
            Integer previous_vote = template.queryForObject(ThreadQueries.checkPreviousVote, Integer.class, user_id,
                    thread.getId());
            if (previous_vote.equals(vote.getVoice())) {
                return thread;
            }
            template.update(ThreadQueries.updateVote, vote.getVoice(), user_id, thread.getId());

            template.update(ThreadQueries.updateVotes, 2 * vote.getVoice(), thread.getId());
            thread.setVotes(thread.getVotes() + 2 * vote.getVoice());

        } catch(IncorrectResultSizeDataAccessException ex) {
            template.update(ThreadQueries.addVote, user_id, thread.getId(), vote.getVoice());

            template.update(ThreadQueries.updateVotes, vote.getVoice(), thread.getId());
            thread.setVotes(thread.getVotes() + vote.getVoice());
        }


        return thread;
    }

    public Integer status() {
        return template.queryForObject(ThreadQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(ThreadQueries.clear);
    }
}
