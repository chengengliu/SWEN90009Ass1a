public class Knight extends Thread{
    private int id;
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private Hall greatHall;

//    private boolean standing = true;
//    private boolean insideHall = false;

    public Knight(int id, Agenda agendaNew, Agenda agendaComplete, Hall greatHall){
        this.id = id;
        this.agendaNew = agendaNew;
        this.agendaComplete = agendaComplete;
        this.greatHall = greatHall;
    }
    @Override
    public void run(){
        while(!isInterrupted()){
            try{
                sleep(Params.MEAN_MINGLING_TIME);
                this.greatHall.knightEnters(this.toString());


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
