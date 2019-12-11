package cn.enjoy.tomcat.container;

/**
 * @author 【享学课堂】 King老师
 * 需要往期视频的同学加QQ：2068425757（肉兰老师）
 * 需要咨询VIP课程的同学加QQ：1011843464 （依娜老师）
 */
public class StandardPipeline implements  Pipeline {
    //阀门（非基础，定义一个first）
    protected Valve first = null;
    //基础阀门
    protected Valve basic = null;

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        this.basic=valve;
    }

    @Override
    public Valve getFirst() {
        return first;
    }

    //添加阀门，链式构建阀门的执行顺序（先定制、最后基础阀门）
    @Override
    public void addVave(Valve valve) {
        if(first == null){
            first = valve;
            valve.setNext(basic);
        }else{
            Valve current =first;
            while(current !=null){
                if(current.getNext() == basic){
                    current.setNext(valve);
                    valve.setNext(basic);
                }
                current = current.getNext();
            }
        }
    }
}
