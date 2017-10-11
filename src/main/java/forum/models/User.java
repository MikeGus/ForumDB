package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class User {

    private String about;
    private String email;
    private String fullname;
    private String nickname;

    public User(@JsonProperty("about") final String about,
                @JsonProperty("email") final String email,
                @JsonProperty("fullname") final String fullname,
                @JsonProperty("nickname") final String nickname) {
        this.about = about;
        this.email = email;
        this.fullname = fullname;
        this.nickname = nickname;
    }

    public final String getAbout() { return about; }

    public final String getEmail() { return email; }

    public final String getFullname() { return fullname; }

    public final String getNickname() { return nickname; }

    public void setAbout(final String about) { this.about = about; }

    public void setEmail(final String email) { this.email = email; }

    public void setFullname(final String fullname) { this.fullname = fullname; }

    public void setNickname(final String nickname) { this.nickname = nickname; }
}
