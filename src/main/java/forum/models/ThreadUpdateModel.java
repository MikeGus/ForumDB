package forum.models;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
public class ThreadUpdateModel {

    private String message;
    private String title;

    public ThreadUpdateModel(final String message, final String title) {
        this.message = message;
        this.title = title;
    }

    public final String getMessage() {
        return message;
    }

    public final String getTitle() {
        return title;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
