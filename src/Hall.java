/**
 * This class acts as a monitor, holding the interaction with Knight, KingArthur.
 * The communication such as when KingArthur has arrived in the Great Hall, will be
 * sent through this class and monitored by the class, so that other threads of Knight
 * will be notified.
 *
 * Several rules to keep in mind:
 * Having entered the GH, a Knight cannot leave without having acquired a new Quest.
 * Having entered the GH, a Knight cannot leave without sitting at the Round Table.
 *
 * @Author: Chengeng Liu
 * @StudentID: 813174
 */
public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;
    private volatile boolean kingInside;
    private volatile boolean isMeetingStart;
    private volatile int knightsInHall;
    private volatile int knightsSitTable;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;

        this.kingInside = false;
        this.knightsInHall = 0;
        this.knightsSitTable = 0;

        this.isMeetingStart = false;
    }

    /**
     * Change the flag showing that the KingArthur has arrived in the Great Hall.
     * From this point, no Knight can leave or enter the Great Hall.
     * @param kingArthur
     */
    synchronized void kingArrive(KingArthur kingArthur) {
        this.kingInside = true;
        System.out.println(kingArthur+"enters the"+this.toString());
        this.notifyAll();
    }

    /**
     * King Arthur leaves the Great Hall. He will only leave the Great Hall if the meeting ends.
     * @param kingArthur King Arthur
     */
    synchronized void kingLeave(KingArthur kingArthur){
        while(this.isMeetingStart){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        this.kingInside = false;
        System.out.println(kingArthur.toString()+"exits the"+this.toString());
        this.notifyAll();
    }

    /**
     * A Knight may leave the Great Hall if the King Arthur is not inside the Great Hall.
     * @param knight Knight
     */
    synchronized void knightLeave(Knight knight){
        while(isKingInside()){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(knight.toString()+" exits from"+this.toString());
        this.knightsInHall--;
        this.notifyAll();
    }

    /**
     * A Knight may enter the Great Hall if the King Arthur is not inside the Great Hall.
     * If the King Arthur is inside the Great Hall, wait outside. If the Knight is holding a quest and the
     * quest is not finished, cannot enter.
     * @param knight
     */
    synchronized void knightEnter(Knight knight){
        while(isKingInside() || (knight.quest!=null&&!knight.quest.completed)){
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

    /**
     * A Knight may sit at the Round Table without limitation. (The Knight has entered the Great Hall with correct
     * precondition). Mingling time applies to the Knight right after he sat down at the Round Table.
     * @param knight
     */
    synchronized void knightSit(Knight knight){
        System.out.println(knight.toString() + " sits at the Round Table");
        this.knightsSitTable++;
        this.notifyAll();
    }

    /**
     * A Knight may stand up from the Round Table after he got the quest.
     * @param knight
     */
    synchronized void knightStandup(Knight knight){
        while(knight.quest==null){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println(knight.toString()+" stands from the Round Table");
        this.knightsSitTable--;
        this.notifyAll();
    }

    /**
     * A method used by the King Arthur, to inform all Knights that the meeting starts.
     * A boolean flag isMeetingStart is set to true.
     * This method is especially important to indicate that the meeting has started, so Knights can release and
     * acquire quests, based on the update of the boolean isMeetingStart.
     * If the number of Knights that sit doesn't equal to the number of Knights that enters the Great Hall,
     * the meeting cannot start.
     */
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

    /**
     * A method used by the King Arthur, to inform all Knights that the meeting ends.
     * A boolean flag isMeetingStart is set to false. If no Knights are sitting, the meeting ends.
     */
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

    /**
     *  A Knight sets off for a quest.
     * @param knight
     */
    synchronized void setOffQuest(Knight knight){
        System.out.println(knight.toString()+" sets off to complete "+knight.quest.toString()+"!");
        notifyAll();
    }

    /**
     * A Knight finished a quest, after a questing time.
     * @param knight Knight
     * @param quest The quest that the Knight has just finished
     */
    synchronized void questFinished(Knight knight, Quest quest){
        System.out.println(knight.toString()+" completes "+quest.toString()+"!");
        quest.completed = true;
        notifyAll();
    }

    /**
     *  A blocking method that prevents a Knight releasing(when the Knight has sit down) a quest, when the meeting
     *  is not started yet.
     */
    synchronized void duringMeeting(){
        while(!this.isMeetingStart){
            try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        notifyAll();
    }

    @Override
    public String toString(){
        return " "+this.tag;
    }

    public boolean isKingInside() {
        return kingInside;
    }
}
