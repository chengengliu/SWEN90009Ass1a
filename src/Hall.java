public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;
    private volatile boolean kingInside;
    private volatile int knightsInHall;
//    private RoundTable roundTable;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;
        this.kingInside = false;
        this.knightsInHall = 0;
//        this.roundTable = new RoundTable();
    }

    synchronized void kingArrive(String name) {
        this.kingInside = true;
        System.out.println(name+"enters"+this.toString());
        this.notifyAll();
    }


    synchronized void kingLeaves(String name){
        this.kingInside = false;
        System.out.println(name+"exists"+this.toString());
        this.notifyAll();
    }

//    When king is inside the hall, no kinght can enter or leaves the hall.
    synchronized void knightLeaves(String name){
        while(isKingInside()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(name+" exits"+this.toString());
        this.knightsInHall--;
        this.notifyAll();
    }
//  这里的判断条件是： 当king在场时不能离开GH了
    synchronized void knightEnters(String name){
//        注意 当king在hall里面的时候 你已经无法进入了，必须等待。
        while(isKingInside()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(name+" enters"+this.toString());
        this.knightsInHall++;
        this.notifyAll();
    }

//  这里的判断条件是： 当king在场时不能离开GH了.但是当knigh走到这里的时候已经进入到了GH。
//    根据spec，当一个knight手上是空的话，他必须来拿quest，当他完成了quest也必须来交quest。不可能出现knight进来又走了的情况。
    synchronized void knighSit(String name){
        System.out.println(name + " sits at the Round Table");
        this.notifyAll();
    }

    synchronized void knightStandup(String name){
        System.out.println(name+" stands from the Round Table");
        this.notifyAll();
    }

    @Override
    public String toString(){
        return " "+this.tag;
    }

    public boolean isKingInside() {
        return kingInside;
    }
}
