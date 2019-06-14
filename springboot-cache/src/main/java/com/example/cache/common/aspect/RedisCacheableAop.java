package com.example.cache.common.aspect;

import com.example.cache.common.annotation.RedisCacheable;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
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
        //方法参数列表
        Object[] args = joinPoint.getArgs();

        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        //方法参数名称列表
        String[] paramNames = u.getParameterNames(method);

        //key前缀，使用类名+方法名
        String baseCacheKey = joinPoint.getTarget().getClass().getName()+"."+method.getName();

        String cacheKey = getCacheableKey(baseCacheKey,key, args,paramNames);

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
     * @param paramNames   方法参数名
     * @param preCacheKey   key前缀（类名+方法名
     * @return
     */
    public String getCacheableKey(String preCacheKey,String key, Object[] args, String[] paramNames) {
        if (StringUtils.isNotBlank(key)) {
            preCacheKey += ".";
            //spring EL表达式获取key值
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(key);
            EvaluationContext context = new StandardEvaluationContext();
            for(int i=0;i<args.length;i++){
                context.setVariable(paramNames[i],args[i]);
            }
            String selResult = null;
            try {
                selResult = expression.getValue(context,String.class);
            } catch (Exception e) {
                //sel无法匹配转换，直接使用key的值
                selResult = key;
            }
            preCacheKey += selResult;
            System.out.println("cacheKey:::::"+preCacheKey);
            return preCacheKey;
        }
        if (null != args && 0 < args.length) {
            preCacheKey += ".";
            for (Object obj:args) {
                preCacheKey += obj.toString();
            }
            return preCacheKey;
        }
        return preCacheKey;//默认前缀 类名+方法名称
    }

    //在使用redis中存储的是json序列化的对象时，需要用来获取需要返回的类，以便手动反序列化
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
