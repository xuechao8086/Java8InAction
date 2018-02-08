package lambdasinaction.xuechao8086.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * aop相关测试： http://www.cnblogs.com/davidwang456/p/5732607.html
 * @author gumi
 * @since 2018/02/07 11:31
 */
public class DynamicProxyDemo {

    /**
     * 动态代理
     */
    private void dynamicProxyTest() {
        //需要代理的接口，被代理类实现的多个接口都必须在这里定义
        Class[] proxyInterface = new Class[] { IBusiness.class, IBusiness2.class };
        //构建AOP的Advice，这里需要传入业务类的实例
        LogInvocationHandler handler = new LogInvocationHandler(new Business());
        //生成代理类的字节码加载器
        ClassLoader classLoader = DynamicProxyDemo.class.getClassLoader();
        //织入器，织入代码并生成代理类
        IBusiness2 proxyBusiness = (IBusiness2) Proxy.newProxyInstance(classLoader, proxyInterface, handler);
        //使用代理类的实例来调用方法。
        proxyBusiness.doSomeThing2();
        ((IBusiness) proxyBusiness).doSomeThing();
    }

    /**
     * 动态字节码生成
     */
    private void cglibProxyTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Business.class);
        enhancer.setCallback(new LogIntercept());
        IBusiness2 newBusiness = (IBusiness2) enhancer.create();
        newBusiness.doSomeThing2();
    }



    public static void main(String[] args) {
        DynamicProxyDemo demo = new DynamicProxyDemo();
        demo.cglibProxyTest();
    }

    public static class LogIntercept implements MethodInterceptor {
        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object rev = proxy.invokeSuper(target, args);
            if (method.getName().equals("doSomeThing2")) {
                System.out.println("记录日志");
            }
            return rev;
        }
    }
}