package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("unused")
public class UserUpdateModel {

    private String about;
    private String email;
    private String fullname;

    public UserUpdateModel(@JsonProperty("about") final String about,
                           @JsonProperty("email") final String email,
                           @JsonProperty("fullname") final String fullname) {
        this.about = about;
        this.email = email;
        this.fullname = fullname;
    }

    public final String getAbout() {
        return about;
    }

    public final String getEmail() {
        return email;
    }

    public final String getFullname() {
        return fullname;
    }

    public void setAbout(final String about) {
        this.about = about;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setFullname(final String fullname) {
        this.fullname = fullname;
    }
}
