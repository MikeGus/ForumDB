package forum.jdbc;

import forum.dao.ForumDAO;
import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import forum.queries.ForumQueries;
import forum.rowmappers.RowMapperCollection;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcForum extends JdbcTemplate implements ForumDAO {

    private DataSource dataSource;

    public JdbcForum(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void create(final ForumModel forum) {
        this.update(ForumQueries.create, forum.getSlug(), forum.getTitle(), forum.getUser());
    }

    public ForumModel getBySlug(final String slug) {
        return this.queryForObject(ForumQueries.getBySlug,
                new Object[] {slug},
                new BeanPropertyRowMapper<>(ForumModel.class));
    }

    public Integer status() {
        return this.queryForObject(ForumQueries.status,
                new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        this.execute(ForumQueries.clear);
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

        return this.query(ForumQueries.getThreads(limit, since, desc),
                args.toArray(), RowMapperCollection.readThread);
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

        return this.query(ForumQueries.getUsers(limit, since, desc),
                args.toArray(), RowMapperCollection.readUser);
    }
}
