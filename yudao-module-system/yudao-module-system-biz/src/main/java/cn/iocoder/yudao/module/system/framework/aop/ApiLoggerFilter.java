package cn.iocoder.yudao.module.system.framework.aop;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.framework.log.BizLogger;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @ClassName: ApiLoggerFilter
 * @Description: TODO
 * @Author: jialiangteng
 * @Date: 2024/11/13 19:08
**/
@Component
@Aspect
@Slf4j
@Order(1)
public class ApiLoggerFilter {
    /**
     * Filter.
     *
     * @param pjp the pjp
     * @return the object
     * @throws Throwable the throwable
     */
    @Around(value = "execution(* cn.iocoder.yudao.module.system.controller..*.*(..))")
    public Object filter(final ProceedingJoinPoint pjp) throws Throwable {
        String target = pjp.getTarget().getClass().getName();
        String method = pjp.getSignature().getName();
        MDC.put("T", UUID.randomUUID().toString());
        log.info("invoke ApiLoggerFilter.filter target:" + target + " method:" + method + ",args:"
                + (pjp.getArgs() != null && pjp.getArgs().length > 0
                ? (pjp.getArgs()[0] != null ? JSONObject.toJSONString(pjp.getArgs()[0]) : null)
                : null));
        CommonResult<?> ret = null;
        long start = System.currentTimeMillis();
        try {
            ret = (CommonResult<?>) pjp.proceed(pjp.getArgs());
        } catch (IllegalArgumentException ilae) {
            log.warn(ilae.getMessage(), ilae);
            ret = null == ret ? new CommonResult<>() : ret;
            ret.setCode(HttpStatus.FORBIDDEN.value());
            ret.setMsg(HttpStatus.FORBIDDEN.name());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            ret = null == ret ? new CommonResult<>() : ret;
            ret.setCode(999);
            ret.setMsg("系统异常");
        } finally {
            ret = null == ret ? new CommonResult<>() : ret;
            BizLogger.logApiEvent(pjp.getTarget().getClass().getCanonicalName(), method,
                    System.currentTimeMillis() - start, String.valueOf(ret.getCode()), ret.getMsg(), null);
            log.info("invoke ApiLoggerFilter.filter target:{},method:{},result :{}", target, method,
                    JSONObject.toJSONString(ret));
            MDC.clear();
        }
        return ret;
    }

    //    /**
    //     * 合翔对接接口的日志切面
    //     *
    //     * @param joinPoint
    //     * @return
    //     * @throws Throwable
    //     */
    //    @Around(value = "execution(* com.creditease.insurance.mall.api.controller.hxbx..*.*(..))")
    //    public Object hxFilter(final ProceedingJoinPoint joinPoint) throws Throwable {
    //        String target = joinPoint.getTarget().getClass().getName();
    //        // 获取方法名
    //        String method = joinPoint.getSignature().getName();
    //        // 获取方法参数
    //        Object[] args = joinPoint.getArgs();
    //        // 记录入参日志
    //        log.info("invoke ApiLoggerFilter.hxFilter target:" + target + " method:" + method + ",args:" + args);
    //        // 执行目标方法并获取返回值
    //        Object result = null;
    //        try {
    //            result = joinPoint.proceed();
    //            // 记录返回值日志
    //        } catch (Throwable e) {
    //            log.error(e.getMessage(), e);
    //        } finally {
    //            log.info("invoke ApiLoggerFilter.hxFilter target:{},method:{},result :{}", target, method, result);
    //        }
    //        return result;
    //    }
}
