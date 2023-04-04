package my.project.wenews.oauth2;

import my.project.wenews.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomDefaultOAuth2User extends DefaultOAuth2User {

    private OAuthAttributes oAuthAttributes;
    private Long memberId;

    public CustomDefaultOAuth2User(Collection<? extends GrantedAuthority> authorities,
                                   Map<String, Object> attributes,
                                   String nameAttributeKey,
                                   OAuthAttributes oAuthAttributes, Member member) {
        super(authorities, attributes, nameAttributeKey);
        this.oAuthAttributes = oAuthAttributes;
        this.memberId = member.getMemberId();
    }
}
