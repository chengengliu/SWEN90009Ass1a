public class Knight extends Thread{
    private int id;
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private Hall greatHall;

    private volatile boolean questFinished;
    Quest quest;

//    private boolean standing = true;
//    private boolean insideHall = false;

    public Knight(int id, Agenda agendaNew, Agenda agendaComplete, Hall greatHall){
        this.id = id;
        this.agendaNew = agendaNew;
        this.agendaComplete = agendaComplete;
        this.greatHall = greatHall;

        this.questFinished = false;
        this.quest = null;
    }
    @Override
    public void run(){
        while(!isInterrupted()){
            try{
//                Not too sure if this time should be placed here?
//                sleep(Params.getMinglingTime());
//                Knight enters GH
                this.greatHall.knightEnter(this);
//                Knight sits at the round table, after the mingling time. (as specified in the spec?)
//                sleep(Params.getMinglingTime());

                this.greatHall.knightSit(this);
//                Knights may discuss about some quests, after they sitting down. Notice that the meeting not start yet.
                sleep(Params.getMinglingTime());
//                Knights could only acquire and release a quest during the meeting.
                this.greatHall.duringMeeting();

//                Knight release quest(if they have finished any).
                agendaComplete.questRelease(this, this.quest);
//                Acquire new quest. Make sure that it is now in the meeting.
//                this.greatHall.duringMeeting();
                this.quest =agendaNew.questAcquire(this);

//                Stand up from the round table.
                this.greatHall.knightStandup(this);
//                After standing up from the round table, the knights have some discussions.
                sleep(Params.getMinglingTime());

//                Knight leaves the Great Hall
                this.greatHall.knightLeave(this);
                sleep(Params.getMinglingTime());

//                Knight set off and trying to finish quest.
                this.greatHall.setOffQuest(this, quest);
//                It took time for the knight to complete the quest.
                sleep(Params.getQuestingTime());
//                Finally the quest has been finished
                this.greatHall.questFinished(this, quest);

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

    public boolean isQuestFinished() {
        return questFinished;
    }
}
