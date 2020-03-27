public class KingArthur extends Thread{
    private Hall greatHall;
    private boolean insideHall;
    private boolean inMeeting;

    public KingArthur(Hall greatHall){
        this.greatHall = greatHall;
        this.insideHall = false;
        this.inMeeting = false;
    }


    @Override
    public void run(){
        // King Arthur's movement is not disturbed by any one.
        while(!isInterrupted()){
            try{
                // Firstly wait 0.8s
//                sleep(Params.getKingWaitingTime());
                // King Arthur enters the hall
                this.greatHall.kingArrive(this.toString());

                this.greatHall.meetingStart();
//                sleep(Params.getKingWaitingTime());
                this.greatHall.meetingEnd();
                // Waiting time for conference
                // Exits the hall.
                this.greatHall.kingLeave(this.toString(),this);
                sleep(Params.getKingWaitingTime());
            }catch (InterruptedException e){
                e.printStackTrace();
                interrupt();
            }

        }
    }
    @Override
    public String toString(){
        return "King Arthur ";
    }
    public void setInsdeHall(boolean insideHall){
        this.insideHall = insideHall;
    }

    public boolean isideHall() {
        return insideHall;
    }

    public void setInMeeting(boolean inMeeting){
        this.inMeeting = inMeeting;
    }

    public boolean inMeeting() {
        return inMeeting;
    }
}
