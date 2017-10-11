package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class Thread {

    private String author;
    private String created;
    private String forum;
    private Integer id;
    private String message;
    private String slug;
    private String title;
    private Integer votes;

    public Thread(@JsonProperty("author") final String author,
                  @JsonProperty("created") final String created,
                  @JsonProperty("forum") final String forum,
                  @JsonProperty("id") final Integer id,
                  @JsonProperty("message") final String message,
                  @JsonProperty("slug") final String slug,
                  @JsonProperty("title") final String title,
                  @JsonProperty("votes") final Integer votes) {
        this.author = author;
        this.created = created;
        this.forum = forum;
        this.id = id;
        this.message = message;
        this.slug = slug;
        this.title = title;
        this.votes = votes;
    }

    public final String getAuthor() { return author; }

    public final String getCreated() { return created; }

    public final String getForum() { return forum; }

    public final Integer getId() { return id; }

    public final String getMessage() { return message; }

    public final String getSlug() { return slug; }

    public final String getTitle() { return title; }

    public final Integer getVotes() { return votes; }

    public void setAuthor(final String author) { this.author = author; }

    public void setCreated(final String created) { this.created = created; }

    public void setForum(final String forum) { this.forum = forum; }

    public void setId(final Integer id) { this.id = id; }

    public void setMessage(final String message) { this.message = message; }

    public void setSlug(final String slug) { this.author = slug; }

    public void setTitle(final String title) { this.title = title; }

    public void setVotes(final Integer votes) { this.votes = votes; }
}
