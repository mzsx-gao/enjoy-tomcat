package cn.enjoy.tomcat.container;

/**
 * @author 【享学课堂】 King老师
 * 需要往期视频的同学加QQ：2068425757（肉兰老师）
 * 需要咨询VIP课程的同学加QQ：1011843464 （依娜老师）
 */
public class Demo {
    public static void main(String[] args) {
        String request ="这个是一个Servlet请求";
        //new出一个管道
        StandardPipeline pipeline = new StandardPipeline();
        //三个阀门(一个基础、2个定制)
        StandardValve standardValve = new StandardValve();
        FirstValve firstValve = new FirstValve();
        SecondValve secondValve = new SecondValve();
        //设置基础阀门(定制阀门)
        pipeline.setBasic(standardValve);
        //设置非基础阀门
        pipeline.addVave(firstValve);
        pipeline.addVave(secondValve);
        //调用对象管道中的第一个阀门
        pipeline.getFirst().invoke(request);
    }
}
