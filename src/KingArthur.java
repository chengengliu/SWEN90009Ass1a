public class KingArthur extends Thread{
    private Hall greatHall;
    private boolean insideHall = false;

    public KingArthur(Hall greatHall){
        this.greatHall = greatHall;
    }


    @Override
    public void run(){
        // King Arthur's movement is not disturbed by any one.
        while(!isInterrupted()){
            try{
                // Fistly wait
                sleep(Params.MEAN_KING_WAITING_TIME);
                // King Arthur enters the hall
                this.greatHall.kingArrive(this.toString());
                // Waiting time for conference
                sleep(Params.getKingWaitingTime());
                // Exits the hall.
                this.greatHall.kingLeaves(this.toString());
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
//    public void setInsdeHall(boolean insideHall){
//        this.insideHall = insideHall;
//    }


}
