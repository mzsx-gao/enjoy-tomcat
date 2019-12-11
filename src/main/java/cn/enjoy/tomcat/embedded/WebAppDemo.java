package cn.enjoy.tomcat.embedded;

import org.apache.catalina.startup.Tomcat;

/**
 * 要用嵌入式启动的方式启动一个SpringMVc的项目
 */
public class WebAppDemo {
    public static void main(String[] args) throws  Exception{
        Tomcat tomcat = new Tomcat();
        tomcat.addWebapp("/ref","D:\\work_tomcat\\ref-comet");
        tomcat.getConnector().setPort(80);
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
