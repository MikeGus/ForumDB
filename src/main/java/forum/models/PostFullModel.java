package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class PostFullModel {

    private UserModel author;
    private ForumModel forum;
    private PostModel post;
    private ThreadModel thread;

    public PostFullModel(@JsonProperty("author") UserModel author,
                         @JsonProperty("forum") ForumModel forum,
                         @JsonProperty("post") PostModel post,
                         @JsonProperty("thread") ThreadModel thread) {
        this.author = author;
        this.forum = forum;
        this.post = post;
        this.thread = thread;
    }

    public UserModel getAuthor() {
        return author;
    }

    public ForumModel getForum() {
        return forum;
    }

    public PostModel getPost() {
        return post;
    }

    public ThreadModel getThread() {
        return thread;
    }
}
