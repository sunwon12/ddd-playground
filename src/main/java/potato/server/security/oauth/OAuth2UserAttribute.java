package potato.server.security.oauth;

import lombok.Data;
import lombok.NoArgsConstructor;
import potato.server.user.domain.User;

import java.util.Map;

/**
 * @Author 정순원
 * @Since 2023-08-19
 */
@NoArgsConstructor
@Data
public abstract class OAuth2UserAttribute {

    private  Map<String, Object> attributes;

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getProviderId();
    public abstract String getEmail();

    public abstract String getName();

    public abstract String getGender();

    public abstract String getBirthday();

    public abstract User toEntity();

    public abstract void setUserAttributesByOauthToken(String authorizionRequestHeader);

}
