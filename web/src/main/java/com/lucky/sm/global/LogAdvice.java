package com.lucky.sm.global;

import com.lucky.sm.entity.Log;
import com.lucky.sm.entity.Staff;
import com.lucky.sm.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LogAdvice {
    @Autowired
    private LogService logService;
    @AfterReturning("mypointcut1()")
    public void operationLog(JoinPoint joinPoint){
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        log.setOperation(joinPoint.getSignature().getName());
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff =(Staff)obj;
        log.setOperator(staff.getAccount());
        log.setResult("成功");
        logService.addOperationLog(log);
    }
    @AfterThrowing(throwing ="e",pointcut ="mypointcut2()")
    public void systemLog(JoinPoint joinPoint,Throwable e){
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        log.setOperation(joinPoint.getSignature().getName());
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        Staff staff =(Staff)obj;
        log.setOperator(staff.getAccount());
        log.setResult(e.getClass().getSimpleName());
        logService.addSystemLog(log);
    }
    @After("mypointcut3()")
    public void loginLog(JoinPoint joinPoint){
        log(joinPoint);
    }
    @Before("mypointcut4()")
    public void logoutLog(JoinPoint joinPoint){
        log(joinPoint);
    }
    private void log(JoinPoint joinPoint){
        Log log = new Log();
        log.setMoudle(joinPoint.getTarget().getClass().getSimpleName());
        log.setOperation(joinPoint.getSignature().getName());
        HttpServletRequest request =(HttpServletRequest) joinPoint.getArgs()[0];
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("USER");
        if(obj==null){
            log.setOperator(request.getParameter("account"));
            log.setResult("失败");
        }else {
            Staff staff = (Staff) obj;
            log.setOperator(staff.getAccount());
            log.setResult("成功");
        }
        logService.addLoginLog(log);
    }

    @Pointcut(value = "execution(* com.lucky.sm.controller.*.*(..)) && " +
            "!execution(* com.lucky.sm.controller.SelfController.*(..)) && " +
            "!execution(* com.lucky.sm.controller.*.to*(..))")
    private void mypointcut1(){}

    @Pointcut(value = "execution(* com.lucky.sm.controller.*.*(..)) && " +
            "!execution(* com.lucky.sm.controller.SelfController.*(..))")
    private void mypointcut2(){}

    @Pointcut(value = "execution(* com.lucky.sm.controller.SelfController.login(..))")
    private void mypointcut3(){}

    @Pointcut(value = "execution(* com.lucky.sm.controller.SelfController.logout(..))")
    private void mypointcut4(){}





}
