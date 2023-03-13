package my.project.wenews.security;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import my.project.wenews.exception.ErrorResponse;
import my.project.wenews.exception.ExceptionCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Gson gson;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        ErrorResponse errorResponse = ErrorResponse.of(ExceptionCode.UNAUTHORIZED_EXCEPTION);
        String content = gson.toJson(errorResponse);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorResponse.getStatus());
        response.getWriter().write(content);
    }
}
