package my.project.wenews.config.auth;

import lombok.RequiredArgsConstructor;
import my.project.wenews.oauth2.Oauth2Service;
import my.project.wenews.oauth2.Oauth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final Oauth2Service oauth2Service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeRequests().antMatchers("/api/auth/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .successHandler(oauth2SuccessHandler)
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장
                .userService(oauth2Service); // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록
        return http.build();




    }


    private class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
//            super.configure(builder);
        }
    }

}
