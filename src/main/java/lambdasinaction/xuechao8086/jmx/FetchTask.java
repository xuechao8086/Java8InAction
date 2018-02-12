package lambdasinaction.xuechao8086.jmx;

/**
 * @author gumi
 * @since 2018/02/12 11:04
 */
public class FetchTask {
    private final String name;

    public FetchTask(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FetchTask: " + name;
    }


}
