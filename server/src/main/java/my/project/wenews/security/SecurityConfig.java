package my.project.wenews.security;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.role.Role;
import my.project.wenews.oauth2.CustomOauth2Service;
import my.project.wenews.oauth2.CustomOauth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomOauth2SuccessHandler customOauth2SuccessHandler;
    private final CustomOauth2Service customOauth2Service;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/api/auth/**").hasRole(Role.USER.name())
                        .anyRequest().permitAll())
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .oauth2Login()
                .successHandler(customOauth2SuccessHandler)
                .defaultSuccessUrl("/")
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 저장
                .userService(customOauth2Service); // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록

        return http.build();


    }


    private class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
//            super.configure(builder);
        }
    }

}
