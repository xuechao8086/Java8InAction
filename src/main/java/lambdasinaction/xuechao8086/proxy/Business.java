package lambdasinaction.xuechao8086.proxy;

/**
 * @author gumi
 * @since 2018/02/07 11:38
 */
public class Business implements IBusiness, IBusiness2{
    @Override
    public boolean doSomeThing() {
        System.out.println("执行业务逻辑");
        return true;
    }

    @Override
    public void doSomeThing2() {
        System.out.println("执行业务逻辑2");
    }

}
