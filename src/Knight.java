/**
 * This class simulates a series of actions that a Knight performs. It encapsulates the data and Multiple threads
 * represent multiple Knights. The Knight keeps three monitors and interact with the monitors.
 *
 * @Author: Chengeng Liu
 * @StudntID: 813174
 */
public class Knight extends Thread{
    private int id;
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private Hall greatHall;

    Quest quest;

    public Knight(int id, Agenda agendaNew, Agenda agendaComplete, Hall greatHall){
        this.id = id;
        this.agendaNew = agendaNew;
        this.agendaComplete = agendaComplete;
        this.greatHall = greatHall;

        this.quest = null;
    }

    /**
     * Simulate a series of actions that a Knight will do.
     * Enter GH, Sit at RT, Release Q, Acquire Q, Stand from RT,
     *     exit GH, Set off to complete Q, Complete Q, repeat the process.
     * Mingling Time is added before the meeting starts(before Knight sits down)
     *  and the meeting ends(after Knight stands up).
     */
    @Override
    public void run(){
        while(!isInterrupted()){
            try{
                this.greatHall.knightEnter(this);

//                Knights may discuss about some quests, before they sit down. Notice that the meeting not start yet.
                sleep(Params.getMinglingTime());

                this.greatHall.knightSit(this);
//                Knights could only acquire and release a quest during the meeting.
                this.greatHall.duringMeeting();

                agendaComplete.questRelease(this, this.quest);
                this.quest =agendaNew.questAcquire(this);

                this.greatHall.knightStandup(this);
//                After standing up from the round table, the knights have some discussions.
                sleep(Params.getMinglingTime());

                this.greatHall.knightLeave(this);

                this.greatHall.setOffQuest(this);
                sleep(Params.getQuestingTime());
                this.greatHall.questFinished(this, this.quest);
            }catch (InterruptedException e){
                e.printStackTrace();
                interrupt();
            }
        }


    }
    @Override
    public String toString(){
        return "Knight "+this.id;
    }
}
