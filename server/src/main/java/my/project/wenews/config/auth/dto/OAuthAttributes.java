package my.project.wenews.config.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.util.Map;


@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String memberId;
    private String email;
    private String picture;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if (registrationId.equals("kakao"))
            return null;
        else
            return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        String id = (String) attributes.get(userNameAttributeName);
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");
        return OAuthAttributes.builder()
                .memberId(id+"@google")
                .email(email)
                .picture(picture)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName).build();
    }

}
