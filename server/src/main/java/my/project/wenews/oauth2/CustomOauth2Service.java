package my.project.wenews.oauth2;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.dto.SessionUser;
import my.project.wenews.member.entity.Member;
import my.project.wenews.member.repository.MemberRepository;
import my.project.wenews.member.role.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Component
public class CustomOauth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(member));

        return new CustomDefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey(), attributes);
    }

    private Member saveOrUpdate(OAuthAttributes oAuthAttributes) {

        String name = oAuthAttributes.getName();
        String email = oAuthAttributes.getEmail();
        String picture = oAuthAttributes.getPicture();

        Member member = memberRepository.findByMemberEmail(oAuthAttributes.getEmail())
                .map(m -> m.update(email, picture, name))
                .orElse(Member.builder().memberName(name).memberEmail(email).role(Role.USER).memberPicture(picture).build());

        return memberRepository.save(member);
    }
}
