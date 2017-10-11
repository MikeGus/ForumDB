package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class Status {

    private Integer forum;
    private Integer post;
    private Integer thread;
    private Integer user;

    public Status(@JsonProperty("forum") final Integer forum,
                  @JsonProperty("post") final Integer post,
                  @JsonProperty("thread") final Integer thread,
                  @JsonProperty("user") final Integer user) {
        this.forum = forum;
        this.post = post;
        this.thread = thread;
        this.user = user;
    }

    public final Integer getForum() {
        return forum;
    }

    public final Integer getPost() {
        return post;
    }

    public final Integer getThread() {
        return thread;
    }

    public final Integer getUser() {
        return user;
    }

    public void setForum(final Integer forum) {
        this.forum = forum;
    }

    public void setPost(final Integer post) {
        this.post = post;
    }

    public void setThread(final Integer thread) {
        this.thread = thread;
    }

    public void setUser(final Integer user) {
        this.user = user;
    }
}
