package lambdasinaction.xuechao8086.annotation;

/**
 * @author gumi
 * @since 2017/12/14 20:02
 */
public class BeanDefine {
    public String id;
    public String className;

    public BeanDefine(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
