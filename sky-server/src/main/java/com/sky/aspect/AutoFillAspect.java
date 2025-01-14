package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * entry point
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /*
    * pre-notify, assign value before the autoFill
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException {
        log.info("start auto fill...");
        //get the current operated database type
        //获取当前被拦截方法的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); //method signature object
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class); //get annotation object with signature
        OperationType operationType = autoFill.value(); // get database operation type

        //prepare the value object
        Object[] args = joinPoint.getArgs();
        if (args == null|| args.length == 0) {
            return;
        }
        Object entity = args[0];
        //get data
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //assign value to the specific target

        if(operationType == OperationType.INSERT) {
            try{
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTIme = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTIme.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);

            }catch (Exception e){
                e.printStackTrace();
            }


        }else if(operationType == OperationType.UPDATE) {
            try{
                Method setUpdateTIme = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTIme.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            }catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
