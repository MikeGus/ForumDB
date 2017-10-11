package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class Forum {

    private Integer posts;
    private String slug;
    private Integer threads;
    private String title;
    private String user;

    public Forum(@JsonProperty("posts") final Integer posts,
                 @JsonProperty("slug") final String slug,
                 @JsonProperty("threads") final Integer threads,
                 @JsonProperty("title") final String title,
                 @JsonProperty("user") final String user) {
        this.posts = posts;
        this.slug = slug;
        this.threads = threads;
        this.title = title;
        this.user = user;
    }

    public final Integer getPosts() {
        return posts;
    }

    public final String getSlug() {
        return slug;
    }

    public final Integer getThreads() {
        return threads;
    }

    public final String getTitle() {
        return title;
    }

    public final String getUser() {
        return user;
    }

    public void setPosts(final Integer posts) {
        this.posts = posts;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public void setThreads(final Integer threads) {
        this.threads = threads;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setUser(final String user) {
        this.user = user;
    }
}
