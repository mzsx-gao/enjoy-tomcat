package cn.enjoy.tomcat.handwrite;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 【享学课堂】 King老师
 *工具类---加载（解析xml ,类加载之类的）
 */
public class ProjectLoader {
    public static Map load() throws Exception{
        //定义一个Map,存储项目信息
        Map<String,Object> webapp = new HashMap<>();

        //servlet
        Map<String, Servlet> servletInstances = new HashMap<>();
        //servlet-mapping
        Map<String, String> servletMapping = new HashMap<>();

        //项目路径
        String webappPath ="D:\\work_public\\servletdemo\\out\\artifacts\\servletdemo_war_exploded\\WEB-INF";

        //JDK提供类加载器 URL
        URL classFile = new URL("file:"+webappPath+"\\classes\\");
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{classFile});


        //解析xml文件
        //1.获取解析器
        SAXReader reader = new SAXReader();
        //2.获取文档对象(xml)
        Document document = reader.read(new File(webappPath+"\\web.xml"));
        //3.获取根元素
        Element root = document.getRootElement();
        //4.获取根元素中下的子元素
        List<Element> childElements = root.elements();
        //5.遍历子元素
        for (Element element:childElements) {
            //6.判断元素名称为servlet的元素
            if ("servlet".equals(element.getName())) {
                //获取servlet-name元素
                Element servletName = element.element("servlet-name");
                //获取servlet-class元素
                Element servletClass = element.element("servlet-class");
                String strServletName = servletName.getText();
                String strServletClass = servletClass.getText();
                System.out.println("servlet:"+strServletName+"="+strServletClass);
                //1.加载到JVM
                Class<?> classzz= urlClassLoader.loadClass(strServletClass);
                //2 创建对象实例(反射) -servlet;
                Servlet servlet  = (Servlet)classzz.newInstance();
                //web.xml的servlet实例化放入hashmap
                servletInstances.put(strServletName, servlet);
            }
            //7.判断元素名称为servlet-mapping的元素
            if ("servlet-mapping".equals(element.getName())) {
                //获取servlet-name元素
                Element servletName = element.element("servlet-name");
                //获取url-pattern元素
                Element urlPattern = element.element("url-pattern");
                String strServletName = servletName.getText();
                String strUrlPattern = urlPattern.getText();
                //web.xml的servlet的Mapping放入hashmap
                System.out.println("servlet-mapping:"+strUrlPattern+"="+strServletName);
                servletMapping.put(strUrlPattern,strServletName);
            }
        }
        webapp.put("servlet",servletInstances);
        webapp.put("servlet-mapping",servletMapping);
        return  webapp;
    }
}
