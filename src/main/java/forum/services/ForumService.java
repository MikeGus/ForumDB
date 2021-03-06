package forum.services;

import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import forum.queries.ForumQueries;

import forum.queries.ForumVisitorsQueries;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static forum.rowmappers.RowMapperCollection.readForum;
import static forum.rowmappers.RowMapperCollection.readThread;
import static forum.rowmappers.RowMapperCollection.readUser;

/**
 * Created by MikeGus on 15.10.17
 */

@Service
public class ForumService {

    private JdbcTemplate template;

    public ForumService(JdbcTemplate template) {
        this.template = template;
    }

    public ForumModel create(final ForumModel forum) {
        template.update(ForumQueries.create, forum.getSlug(), forum.getTitle(), forum.getUser(), forum.getUser());
        return getBySlug(forum.getSlug());
    }

    public ForumModel getBySlug(final String slug) {
        return template.queryForObject(ForumQueries.getBySlug, readForum, slug);
    }

    public Integer status() {
        return template.queryForObject(ForumQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(ForumQueries.clear);
    }

    public void clearVisitors() {
        template.execute(ForumVisitorsQueries.clear);
    }

    public List<ThreadModel> getThreads(final String slug, final Integer limit, final String since, final Boolean desc) {

        List<Object> args = new ArrayList<>();
        args.add(slug);
        if (since != null) {
            args.add(since);
        }
        if (limit != null) {
            args.add(limit);
        }

        return template.query(ForumQueries.getThreads(limit, since, desc),
                args.toArray(), readThread);
    }

    public List<UserModel> getUsers(final String slug, final Integer limit, final String since, final Boolean desc) {

        Integer forumId = template.queryForObject(ForumQueries.getIdBySlug, Integer.class, slug);
        return template.query(ForumQueries.getUsers(limit, since, desc), readUser, forumId);
    }
}
