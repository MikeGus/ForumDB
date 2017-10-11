package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class PostFull {

    private User author;
    private Forum forum;
    private Post post;
    private Thread thread;

    public PostFull(@JsonProperty("author") User author,
                    @JsonProperty("forum") Forum forum,
                    @JsonProperty("post") Post post,
                    @JsonProperty("thread") Thread thread) {
        this.author = author;
        this.forum = forum;
        this.post = post;
        this.thread = thread;
    }

    public User getAuthor() { return author; }

    public Forum getForum() { return forum; }

    public Post getPost() { return post; }

    public Thread getThread() { return thread; }
}
