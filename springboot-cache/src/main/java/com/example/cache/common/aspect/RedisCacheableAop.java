package com.example.cache.common.aspect;

import com.example.cache.common.annotation.RedisCacheable;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
public class RedisCacheableAop {

    @Autowired
    private RedisTemplate redisTemplate;

    //切入点:方法上带有@RedisCacheable注解的方法
    @Pointcut("@annotation(com.example.cache.common.annotation.RedisCacheable)")
    public void pointCut() {
    }

    @Around( "pointCut()")
    public Object redisCache(ProceedingJoinPoint joinPoint)  throws Throwable{
        Method method = getMethod(joinPoint);
        RedisCacheable cacheable = method.getAnnotation(RedisCacheable.class);
        String key = cacheable.key();
        Object[] args = joinPoint.getArgs();
        String methodName = method.getName();
        String cacheKey = getCacheableKey(key, args,methodName);
        Object value = redisTemplate.opsForValue().get(cacheKey);
        if(value!=null){
            return value;
        }else {
            Object result = joinPoint.proceed();
            int expireTime = cacheable.expireTime();
            TimeUnit unit = cacheable.unit();
            redisTemplate.opsForValue().set(cacheKey,result,expireTime,unit);
            return result;
        }

    }

    private Method getMethod(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod();
    }
    /**
     * 获取要缓存的key 默认值为查询条件的第一个参数 可以通过key属性指定key
     *
     * @param key       存储的key
     * @param args      方法入参参数
     * @param methodName 方法名称,当方法没有入参时且没有指定key默认使用方法名称作为key
     * @return
     */
    public String getCacheableKey(String key, Object[] args,String methodName) {
        String cacheKey = "";
        if (!StringUtils.isNotBlank(key)) {
            return cacheKey += key;
        }
        if (null != args && 0 < args.length) {
            return cacheKey += args[0];
        }
        return methodName;//方法名称
    }

    private Class getReturnActualType(Method method) throws ClassNotFoundException {
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            Type[] actualTypes = ((ParameterizedType) genericReturnType).getActualTypeArguments();
            for (Type actualType : actualTypes) {
                return Class.forName(actualType.getTypeName());
            }
        }
        return null;
    }
}
