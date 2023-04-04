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

        //DefaultOAuth2User 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //네이버 로그인인지 구글로그인인지 서비스를 구분해주는 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행시 키가 되는 필드값 프라이머리키와 같은 값 네이버 카카오 지원 x
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 데이터를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        Member member = saveOrUpdate(attributes);

        httpSession.setAttribute("user", new SessionUser(member));

        return new CustomDefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey(), attributes, member);
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
