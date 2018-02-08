package lambdasinaction.xuechao8086.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author gumi
 * @since 2018/02/07 11:32
 */
public class LogInvocationHandler implements InvocationHandler{

    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object  invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //执行原有逻辑
        Object rev = method.invoke(target, args);
        //执行织入的日志，你可以控制哪些方法执行切入逻辑
        if (method.getName().equals("doSomeThing2")) {
            System.out.println("记录日志");
        }
        return rev;
    }
}
