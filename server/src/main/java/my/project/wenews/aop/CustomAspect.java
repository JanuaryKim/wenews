package my.project.wenews.aop;


import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.ui.Model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@RequiredArgsConstructor
public class CustomAspect {

    private final PlatformTransactionManager transactionManager;

    @Value("${path.site_path}")
    String sitePath;

    @Around("@annotation(my.project.wenews.annotation.ImageShown)") //NeedMemberId를 붙인 메소드에
    public Object apply(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] parameterValues = joinPoint.getArgs(); //우선 현재 Controller로 넘어 온 파라미터 값들을 가져 옴

        //조인 포인트 (호출 되는 메소드를 말한다) 가 호출되는 시점에 넘겨 받은 인자값들
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //메소드의 선언 부분에 대한 정보
        Method method = signature.getMethod(); //메소드 자체의 대한 정보를 갖는 클래스
        Parameter[] parameters = method.getParameters(); //메소드가 갖는 파라미터들의 정보

        for (int i = 0; i < parameterValues.length; i++) {
            if (parameters[i].getName().equals("model")) { //i번째 파라미터의 이름이 "model"일 경우
                Model model = (Model) parameterValues[i];
                model.addAttribute("site_path", sitePath);
                break;
            }
        }

        Object object = joinPoint.proceed(parameterValues); //새롭게 설정된 파라미터 값들을 해당 메소드에 전달
        return object;
    }
}
