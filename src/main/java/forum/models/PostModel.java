package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class PostModel {

    private String author;
    private String created;
    private String forum;
    private Integer id;
    private Boolean isEdited;
    private String message;
    private Integer parent;
    private Integer thread;

    public PostModel(@JsonProperty("author") final String author,
                     @JsonProperty("created") final String created,
                     @JsonProperty("forum") final String forum,
                     @JsonProperty("id") final Integer id,
                     @JsonProperty("isEdited") final Boolean isEdited,
                     @JsonProperty("message") final String message,
                     @JsonProperty("parent") final Integer parent,
                     @JsonProperty("thread") final Integer thread) {
        this.author = author;
        this.created = created;
        this.forum = forum;
        this.id = id;
        this.isEdited = isEdited;
        this.message = message;
        this.parent = parent;
        this.thread = thread;
    }

    public final String getAuthor() {
        return author;
    }

    public final String getCreated() {
        return created;
    }

    public final String getForum() {
        return forum;
    }

    public final Integer getId() {
        return id;
    }

    public final Boolean getIsEdited() {
        return isEdited;
    }

    public final String getMessage() {
        return message;
    }

    public final Integer getParent() {
        return parent;
    }

    public final Integer getThread() {
        return thread;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setCreated(final String created) {
        this.created = created;
    }

    public void setForum(final String forum) {
        this.forum = forum;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setIsEdited(final Boolean isEdited) {
        this.isEdited = isEdited;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setParent(final Integer parent) {
        this.parent = parent;
    }

    public void setThread(final Integer thread) {
        this.thread = thread;
    }
}
