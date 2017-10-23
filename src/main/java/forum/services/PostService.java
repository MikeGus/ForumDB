package forum.services;

import forum.models.*;
import forum.queries.ForumQueries;
import forum.queries.PostQueries;
import forum.queries.ThreadQueries;
import forum.queries.UserQueries;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static forum.rowmappers.RowMapperCollection.*;

/**
 * Created by MikeGus on 15.10.17
 */

@Transactional
@Service
public class PostService {

    private JdbcTemplate template;

    public PostService(JdbcTemplate template) {
        this.template = template;
    }

    public List<PostModel> create(final String slug_or_id, final List<PostModel> posts) {

        Integer threadId = slug_or_id.matches("\\d+") ?
                template.queryForObject(ThreadQueries.checkThreadPresence, Integer.class, Integer.valueOf(slug_or_id)) :
                template.queryForObject(ThreadQueries.getThreadIdBySlug, Integer.class, slug_or_id);


        if (posts.size() == 0) {
            return posts;
        }

        Integer forumId = template.queryForObject(ThreadQueries.getForumIdByThreadSlugOrId(slug_or_id),
                Integer.class, slug_or_id);

        String currentTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

        List<Object> params = new ArrayList<>();
        for (PostModel post : posts) {
            Integer user_id = template.queryForObject(UserQueries.getIdByNickname, Integer.class, post.getAuthor());
            params.add(user_id);
            if (post.getCreated() != null) {
                params.add(post.getCreated());
            } else {
                params.add(currentTime);
            }
            params.add(forumId);
            params.add(post.getMessage());
            Integer id = 0;
            if (post.getParent() != null && post.getParent() != 0) {
                try {
                    id = template.queryForObject(PostQueries.checkParentId, Integer.class, post.getParent(),
                            threadId);
                } catch (IncorrectResultSizeDataAccessException ex) {
                    throw new NoSuchElementException("Parent not found!");
                }
            }
            post.setParent(id);
            params.add(id);
            params.add(threadId);
        }

        String forumSlug = template.queryForObject(ForumQueries.getSlugById, String.class, forumId);
        List<PostModel> list = template.query(PostQueries.create(posts.size()), readPostData,
                params.toArray());

        Iterator<PostModel> dest = posts.iterator();
        Iterator<PostModel> src = list.iterator();
        while (dest.hasNext() && src.hasNext()) {
            PostModel destPost = dest.next();
            PostModel srcPost = src.next();

            destPost.setCreated(srcPost.getCreated());
            destPost.setIsEdited(Boolean.FALSE);

            destPost.setId(srcPost.getId());
            destPost.setForum(forumSlug);
            destPost.setThread(threadId);
        }

        template.update(PostQueries.updatePostCount, posts.size(), forumId);

        return posts;
    }

    @SuppressWarnings("ConstantConditions")
    public PostFullModel getByIdFull(final Integer id, List<String> related) {

        PostModel post = template.queryForObject(PostQueries.getById, readPost, id);
        PostFullModel result = new PostFullModel(null, null, null, null);
        result.setPost(post);
        
        if (related != null) {
            for (String elem : related) {
                switch (elem) {
                    case "user":
                        UserModel author = template.queryForObject(UserQueries.getByNickname, readUser, post.getAuthor());
                        result.setAuthor(author);
                        break;
                    case "forum":
                        ForumModel forum = template.queryForObject(ForumQueries.getBySlug, readForum, post.getForum());
                        result.setForum(forum);
                        break;
                    case "thread":
                        ThreadModel thread = template.queryForObject(ThreadQueries.getById, readThread, post.getThread());
                        result.setThread(thread);
                        break;
                    default:
                        break;
                }
            }
        }
        
        return result;
    }

    public PostModel update(final Integer id, final PostUpdateModel post) {
        template.update(PostQueries.update, post.getMessage(), id);
        return template.queryForObject(PostQueries.getById, readPost, id);
    }

    public Integer status() {
        return template.queryForObject(PostQueries.status, Integer.class);
    }

    public void clear() {
        template.execute(PostQueries.clear);
    }
}
