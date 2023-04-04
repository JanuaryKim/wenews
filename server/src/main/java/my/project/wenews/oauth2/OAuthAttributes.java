package my.project.wenews.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.io.Serializable;
import java.util.Map;


@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes implements Serializable{
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private Long memberId;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

        if (registrationId.equals("kakao"))
            return null;
        else
            return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        String picture = (String) attributes.get("picture");
        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName).build();
    }

}
