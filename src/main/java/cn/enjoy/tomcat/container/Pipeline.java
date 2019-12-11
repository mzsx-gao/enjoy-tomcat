package cn.enjoy.tomcat.container;
//管道接口
public interface Pipeline {
    //获取第一个阀门
    public Valve getFirst();
    public Valve getBasic();
    //设置阀门
    public void setBasic(Valve valve);
    //添加阀门
    public void addVave(Valve valve);
}
