public class KingArthur extends Thread{
    private Hall greatHall;
    private boolean insideHall = false;

    public KingArthur(Hall greatHall){
        this.greatHall = greatHall;
    }
    @Override
    public void run(){

    }
    public void setInsdeHall(boolean insideHall){
        this.insideHall = insideHall;
    }
}
