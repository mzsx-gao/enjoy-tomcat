package cn.enjoy.tomcat.embedded;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * 使用嵌入式启动的方式启动一个Servlet
 */
public class ServletDemo {

    public static void main(String[] args) throws Exception {

        //自定义的一个Servlet(专门处理http请求)
        HttpServlet httpServlet = new HttpServlet() {
            @Override
            public void service(ServletRequest req, ServletResponse res) throws IOException {
                res.getWriter().write("hello world!");
            }
        };
        //引入嵌入式Tomcat
        Tomcat tomcat = new Tomcat();
        //部署应用的context
        Context context = tomcat.addContext("/demo",null);

        //相当于往应用中添加Servlet
        tomcat.addServlet(context,"hello",httpServlet);

        //相当于添加了servletMapping 映射信息
        context.addServletMappingDecoded("/hello","hello");

        //启动Tomcat  ---生命周期
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();//用于阻塞Tomcat,等待请求过来
        //http://localhost:8080/demo/hello

    }
}