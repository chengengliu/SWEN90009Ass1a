import com.sun.org.apache.xalan.internal.xsltc.runtime.InternalRuntimeError;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;
    private volatile boolean kingInside;
    private volatile boolean isMeetingStart;
    private volatile int knightsInHall;
    private volatile int knightsSitTable;
//    private RoundTable roundTable;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;

        this.kingInside = false;
        this.knightsInHall = 0;
        this.knightsSitTable = 0;

        this.isMeetingStart = false;
//        this.roundTable = new RoundTable();
    }
    synchronized void duringMeeting(){
        while(!this.isMeetingStart){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    synchronized void kingArrive(String name) {
        this.kingInside = true;
        System.out.println(name+"enters the"+this.toString());
        this.notifyAll();
    }


    synchronized void kingLeave(String name, KingArthur kingArthur){
        while(kingArthur.inMeeting()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        kingArthur.setInsdeHall(false);
        this.kingInside = false;
        System.out.println(name+"exits the"+this.toString());
        this.notifyAll();
    }

//    When king is inside the hall, no kinght can enter or leaves the hall.
    synchronized void knightLeave(String name){
        while(isKingInside()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(name+" exits from"+this.toString());
        this.knightsInHall--;
        this.notifyAll();
    }
//  这里的判断条件是： 当king在场时不能离开GH了
    synchronized void knightEnter(Knight knight){
//        注意 当king在hall里面的时候 你已经无法进入了，必须等待。
        while(isKingInside()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println(knight.toString()+" enters"+this.toString());
        this.knightsInHall++;
        this.notifyAll();


    }

//  这里的判断条件是： 当king在场时不能离开GH了.但是当knigh走到这里的时候已经进入到了GH。
//    根据spec，当一个knight手上是空的话，他必须来拿quest，当他完成了quest也必须来交quest。不可能出现knight进来又走了的情况。
//    From the spec:
//    Having entered the GH, a Knight cannot leave without having acquired a new Quest.
//    Having entered the GH, a Knight cannot leave without sitting at the Round Table.
    synchronized void knightSit(String name){
        System.out.println(name + " sits at the Round Table");
        this.knightsSitTable++;
        this.notifyAll();
    }

    synchronized void knightStandup(String name){
        System.out.println(name+" stands from the Round Table");
        this.knightsSitTable--;
        this.notifyAll();
    }

    synchronized void meetingStart(){
        while(this.knightsSitTable != this.knightsInHall || !kingInside){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.isMeetingStart = true;
        System.out.println("Meeting begins!");
        notifyAll();
    }

    synchronized void meetingEnd(){
        while(this.knightsSitTable!=0 || !kingInside){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.isMeetingStart = false;
        System.out.println("Meeting ends!");
        notifyAll();
    }

    synchronized void setOffQuest(Knight knight, Quest quest){
        System.out.println(knight.toString()+" sets off to complete "+quest.toString());
        notifyAll();
    }

    synchronized void questFinished(Knight knight, Quest quest){
        while(quest.completed){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(knight.toString()+" completes "+quest.toString());
        quest.completed = true;
        notifyAll();
    }

    @Override
    public String toString(){
        return " "+this.tag;
    }

    public boolean isKingInside() {
        return kingInside;
    }
    public boolean isMeetingStart(){
        return this.isMeetingStart;
    }
}
