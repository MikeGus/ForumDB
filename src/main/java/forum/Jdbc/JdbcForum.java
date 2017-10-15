package forum.Jdbc;

import forum.DAO.ForumDAO;
import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import forum.queries.ForumQueries;
import forum.rowmappers.ThreadRowMapper;
import forum.rowmappers.UserRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcForum extends JdbcDaoSupport implements ForumDAO {

    JdbcForum(JdbcTemplate template) {
        setJdbcTemplate(template);
    }

    public void create(final ForumModel forum) {
        getJdbcTemplate().update(ForumQueries.create(),
                new Object[] {forum.getSlug(), forum.getTitle(), forum.getUser()});
    }

    public ForumModel getBySlug(final String slug) {
        return getJdbcTemplate().queryForObject(ForumQueries.getBySlug(),
                new Object[] {slug},
                new BeanPropertyRowMapper<>(ForumModel.class));
    }

    public Integer status() {
        return getJdbcTemplate().queryForObject(ForumQueries.status(),
                new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        getJdbcTemplate().execute(ForumQueries.clear());
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

        return getJdbcTemplate().query(ForumQueries.getThreads(limit, since, desc),
                args.toArray(), new ThreadRowMapper());
    }

    public List<UserModel> getUsers(final String slug, final Integer limit, final String since, final Boolean desc) {

        List<Object> args = new ArrayList<>();
        args.add(slug);
        if (since != null) {
            args.add(since);
        }
        if (limit != null) {
            args.add(limit);
        }

        return getJdbcTemplate().query(ForumQueries.getUsers(limit, since, desc),
                args.toArray(), new UserRowMapper());
    }
}
