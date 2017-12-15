package lambdasinaction.xuechao8086.annotation;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gumi
 * @since 2017/12/14 19:50
 */
public class ClassPathXMLApplicationContext {
    private Logger log = Logger.getLogger(ClassPathXMLApplicationContext.class);

    private List<BeanDefine> beanList;
    private Map<String, Object> sigletions;
    private Map<String, String> definitionMap;

    ClassPathXMLApplicationContext(String filename) throws Exception{
        //this.readXML(filename);
        this.loadDefinition();
        this.readDefinition();
        this.instancesBean();
        this.annotationInject();
    }

    /**
     * 总是文件读取错误，换掉
     * @param fileName
     */
    //@SuppressWarnings("unchecked")
    private void readXML(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        SAXReader saxReader = new SAXReader();
        try {
            //Document document = saxReader.read(classLoader.getResourceAsStream(fileName));
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            Document document = saxReader.read(fileInputStream);
            Element beans = document.getRootElement();
            for(Iterator<Element> beansList = beans.elementIterator(); beansList.hasNext(); ) {
                Element element = beansList.next();
                BeanDefine beanDefine = new BeanDefine(element.attributeValue("id"), element.attributeValue("class"));
                beanList.add(beanDefine);
            }
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            log.info("read " + fileName + "error....");
        }
    }

    private void loadDefinition() {
        definitionMap = new HashMap<>(4);
        definitionMap.put("userDao", "lambdasinaction.xuechao8086.annotation.UserDaoImpl");
        definitionMap.put("user1Dao", "lambdasinaction.xuechao8086.annotation.User1DaoImpl");
        definitionMap.put("user2Dao", "lambdasinaction.xuechao8086.annotation.User2DaoImpl");
        definitionMap.put("userService", "lambdasinaction.xuechao8086.annotation.UserServiceImpl");
    }

    private void readDefinition() {
        beanList = definitionMap.entrySet()
            .stream()
            .map(e -> new BeanDefine(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }

    private void instancesBean(){
        sigletions = beanList.stream().collect(Collectors.toMap(BeanDefine::getId, p -> this.instanceBean(p.getClassName())));
    }

    private Object instanceBean(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    public void annotationInject() throws Exception{
        for(String beanName:sigletions.keySet()){
            Object bean = sigletions.get(beanName);
            if(bean!=null){
                this.propertyAnnotation(bean);
                this.fieldAnnotation(bean);
            }
        }
    }

    private Object newInstance(BeanDefine beanDefine) {
        try {
            return Class.forName(beanDefine.getClassName()).newInstance();
        } catch (ClassNotFoundException e) {
            log.error(beanDefine.getId() + " not found");
        } catch (InstantiationException e) {
            log.error(beanDefine.getId() + " instantiation fail");
        } catch (IllegalAccessException e) {
            log.error(beanDefine.getId() + "illegalAccess");
        }
        return null;
    }

    private void propertyAnnotation(Object bean) throws Exception{
        PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
        for(PropertyDescriptor proderdesc : ps) {
            Method setter = proderdesc.getWriteMethod();
            if(setter != null && setter.isAnnotationPresent(ZxfResource.class)) {
                ZxfResource resource = setter.getAnnotation(ZxfResource.class);
                String name = "";
                Object value = null;

                if(!"".equalsIgnoreCase(resource.name())) {
                    name = resource.name();
                    value = sigletions.get(name);
                } else {
                    Optional<Object> optional = sigletions.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .filter(e -> proderdesc.getPropertyType().isAssignableFrom(e.getClass()))
                        .findAny();
                    if(optional.isPresent()) {
                        value = optional.get();
                    }
                }

                setter.setAccessible(true);
                setter.invoke(bean, value);
            }
        }
    }

    private void fieldAnnotation(Object bean) throws Exception{
        Field[] fields = bean.getClass().getFields();
        for(Field f : fields) {
            if(f != null && f.isAnnotationPresent(ZxfResource.class)) {
                ZxfResource resource = f.getAnnotation(ZxfResource.class);
                String name = "";
                Object value = null;

                if(!"".equals(resource.name())) {
                    name = resource.name();
                    value = sigletions.get(name);
                } else {
                    Optional<Object> optional = sigletions.values()
                        .stream()
                        .filter(v -> f.getType().isAssignableFrom(v.getClass()))
                        .findAny();
                    if(optional.isPresent()) {
                        value = optional.get();
                    }
                }
                f.setAccessible(true);
                f.set(bean, value);
            }
        }
    }

    public Object getBean(String beanId) {
        return sigletions.get(beanId);
    }

    public static void main(String[] args) throws Exception{
        String xml = "/Users/gumi/IdeaProjects/Java8InAction/src/main/java/lambdasinaction/xuechao8086/annotation/ClassPathXMLApplicationContext.java";
        ClassPathXMLApplicationContext path = new ClassPathXMLApplicationContext(xml);
        UserServiceImpl userService =(UserServiceImpl)path.getBean("userService");
        userService.show();
    }
}
