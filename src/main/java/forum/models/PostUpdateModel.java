package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
public class PostUpdateModel {

    private String message;

    public PostUpdateModel(@JsonProperty("message") final String message) {
        this.message = message;
    }

    public final String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
