package forum.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by MikeGus on 11.10.17
 */

@SuppressWarnings("unused")
public class Vote {

    private String nickname;
    private Integer voice;

    public Vote(@JsonProperty("nickname") final String nickname,
                @JsonProperty("voice") final Integer voice) {
        this.nickname = nickname;
        this.voice = voice;
    }

    public final String getNickname() {
        return nickname;
    }

    public final Integer getVoice() {
        return voice;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public void setVoice(final Integer voice) {
        this.voice = voice;
    }
}
