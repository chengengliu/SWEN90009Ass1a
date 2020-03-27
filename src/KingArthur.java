/**
 *  This class simulates the movement routine of the King Arthur.
 *  There are four actions he mainly performed. After the King Arthur left the Great Hall,
 *  there is a kingwaiting time for him to be away.
 *
 * @Author: Chengeng Liu
 * @StudentID: 813174
 */
public class KingArthur extends Thread{
    private Hall greatHall;

    public KingArthur(Hall greatHall){
        this.greatHall = greatHall;
    }

    /**
     * Simulation of The King Arthur series of actions
     * The King Arthur general movements:
     *      enter GH, start meeting, end meeting, exit GH.
     */
    @Override
    public void run(){
        // King Arthur's movement is not disturbed by any one.
        while(!isInterrupted()){
            try{
                // King Arthur enters the hall
                this.greatHall.kingArrive(this);
                this.greatHall.meetingStart();
                this.greatHall.meetingEnd();
                this.greatHall.kingLeave(this);
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
}
