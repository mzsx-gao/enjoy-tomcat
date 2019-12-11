package cn.enjoy.tomcat.classloader;

import com.sun.nio.zipfs.ZipPath;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * @author 【享学课堂】 King老师
 * 类加载机制
 */
//自定义一个类加载器
public class TomcatClassLoad extends ClassLoader{

    public static void main(String[] args) throws  Exception{


        //HashMap hashMap = new HashMap();
        //上下文类加载器
        System.out.println("上下文类加载器"+Thread.currentThread().getContextClassLoader());


       // System.out.println("1.启动类加载器:"+HashMap.class.getClassLoader());

       // System.out.println("2.拓展类加载器:"+ZipPath.class.getClassLoader());

//        System.out.println("3.应用程序类加载器:"+TomcatClassLoad.class.getClassLoader());
//
        TomcatClassLoad tomcatClassLoad = new TomcatClassLoad(); //自定的类加载器

        Thread.currentThread().setContextClassLoader(tomcatClassLoad);


//        //这个obj对象就是通过我自己的写的类加载器,去加载的
        Object obj=tomcatClassLoad.loadClass("cn.enjoy.tomcat.classloader.TomcatClassLoad").newInstance();
        System.out.println("obj:"+obj.getClass().getClassLoader());
        System.out.println("这两个类是否相等:" +(obj instanceof   TomcatClassLoad) ); //是不是相等

        classpath();

    }


    //打破双亲委派必须重写loadClass
   @Override
    public final Class<?>loadClass(String name)throws ClassNotFoundException{
        try{
            //获取编译后的class
            String fileName=name.substring(name.lastIndexOf(".")+1)+".class";
            //从class中读取字节数组
            InputStream is=getClass().getResourceAsStream(fileName);
            if(is==null){
                return super.loadClass(name);
            }
            byte[]b=new byte[is.available()];
            is.read(b);
            //使用父类的方法将字节数组转换为class
            return defineClass(name,b,0,b.length);
        }catch(IOException e){
            throw new ClassNotFoundException(name);
        }
    }

    public static void classpath(){
        System.out.println("BootstrapClassLoader 的加载路径: ");

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls)
            System.out.println(url);
        System.out.println("----------------------------");

        //取得扩展类加载器
        URLClassLoader extClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader().getParent();

        System.out.println(extClassLoader);
        System.out.println("扩展类加载器 的加载路径: ");

        urls = extClassLoader.getURLs();
        for(URL url : urls)
            System.out.println(url);

        System.out.println("----------------------------");


        //取得应用(系统)类加载器
        URLClassLoader appClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();

        System.out.println(appClassLoader);
        System.out.println("应用(系统)类加载器 的加载路径: ");

        urls = appClassLoader.getURLs();
        for(URL url : urls)
            System.out.println(url);

        System.out.println("----------------------------");


    }
}
