package my.project.wenews.security.auth;

import lombok.RequiredArgsConstructor;
import my.project.wenews.member.dto.SessionUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver { //리졸버 - 핸들러메소드(컨트롤러의 메소드)의 파라미터와 객체(데이터)를 바인딩 시켜주는 클래스

    private final HttpSession httpSession;


    //본 리졸버가
    @Override
    public boolean supportsParameter(MethodParameter parameter) { //캐시 기능이 가능. 한번 요청 받은 핸들러 메소드를 본 메소드를 거치지 않음

        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());


        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}
