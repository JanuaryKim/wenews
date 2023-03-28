package my.project.wenews.config;

import lombok.RequiredArgsConstructor;
import my.project.wenews.security.auth.LoginUserArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer { //사용자로부터 들어오는 웹 요청의 대한 사전처리 관련하여 설정자역할

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Value("${path.connect-path}")
    private String connectPath;

    @Value("${path.file-path}")
    private String filePath;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserArgumentResolver);
    }

    //리소스 접근 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath + "**")
                .addResourceLocations("file:/" + filePath);
//                .addResourceLocations("file://" + filePath);
    }
}
