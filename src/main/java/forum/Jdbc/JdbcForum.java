package forum.Jdbc;

import forum.DAO.ForumDAO;
import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;
import forum.rowmappers.ThreadRowMapper;
import forum.rowmappers.UserRowMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikeGus on 15.10.17
 */

public class JdbcForum extends JdbcDaoSupport implements ForumDAO {

    public void create(final ForumModel forum) {
        String sql = "INSERT INTO forums (slug, title, user) " +
                "VALUES (?, ?, (SELECT id FROM users WHERE nickname = ?))";
        getJdbcTemplate().update(sql, new Object[] {forum.getSlug(), forum.getTitle(), forum.getUser()});
    }

    public ForumModel getBySlug(final String slug) {
        String sql = "SELECT (*) FROM forums WHERE slug = ?";
        return getJdbcTemplate().queryForObject(sql, new Object[] {slug}, new BeanPropertyRowMapper<>(ForumModel.class));
    }

    public Integer status() {
        String sql = "SELECT COUNT(*) FROM forums";
        return getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }

    public void clear() {
        String sql = "DELETE FROM forums";
        getJdbcTemplate().execute(sql);
    }

    public List<ThreadModel> getThreads(final String slug, final Integer limit, final String since, final Boolean desc) {
        String sqlAllThreads = "SELECT u.nickname, t.created, f.slug, t.id, t.message, t.slug, t.title, t.votes" +
        "FROM threads t JOIN users u ON (t.user_id = u.id) JOIN forums f ON (f.id == t.forum_id) WHERE f.slug = ?";
        StringBuilder builder = new StringBuilder(sqlAllThreads);

        List<Object> args = new ArrayList<>();
        args.add(slug);

        if (since != null) {
            builder.append(" AND t.created >= ?");
            args.add(since);
        }

        builder.append(" ORDER BY t.created");
        if (desc == Boolean.TRUE) {
            builder.append(" DESC");
        }

        if (limit != null) {
            builder.append(" LIMIT ?");
            args.add(limit);
        }

        return getJdbcTemplate().query(builder.toString(), args.toArray(), new ThreadRowMapper());
    }

    public List<UserModel> getUsers(final String slug, final Integer limit, final String since, final Boolean desc) {
        String sqlAllUsers = "SELECT u.about, u.email, u.fullname, u.nickname" +
                "FROM users u JOIN forums f ON (t.user_id = u.id) WHERE f.slug = ?";
        StringBuilder builder = new StringBuilder(sqlAllUsers);

        List<Object> args = new ArrayList<>();
        args.add(slug);

        if (since != null) {
            builder.append(" AND u.nickname >= ?");
            args.add(since);
        }

        builder.append(" ORDER BY u.nickname");
        if (desc == Boolean.TRUE) {
            builder.append(" DESC");
        }

        if (limit != null) {
            builder.append(" LIMIT ?");
            args.add(limit);
        }

        return getJdbcTemplate().query(builder.toString(), args.toArray(), new UserRowMapper());
    }
}
