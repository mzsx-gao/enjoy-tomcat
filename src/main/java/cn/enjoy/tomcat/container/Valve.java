package cn.enjoy.tomcat.container;
//阀门接口
public interface Valve {
    public Valve getNext();
    public void setNext(Valve valve);
    public void invoke(String handing);
}
