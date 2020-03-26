public class Knight extends Thread{
    private int id;
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private Hall greatHall;

    private volatile boolean questFinished;
    private Quest quest;

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
                sleep(Params.getMinglingTime());
//                Knight enters GH
                this.greatHall.knightEnters(this.toString());
//                Knight sits at the round table.
                this.greatHall.knighSit(this.toString());
//                Knight release quest(if they have finished any).

                this.quest = this.agendaNew.questAcquire(this);

//              TODO:Not too sure if this sleep() time is used correctly.
//                  sleep(Params.getQuestingTime());

                this.greatHall.knightStandup(this.toString());

                this.greatHall.knightLeaves(this.toString());

                sleep(Params.getQuestingTime());

                this.agendaComplete.questRelease(this, this.quest);

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
