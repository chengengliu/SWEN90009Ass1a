public class Knight extends Thread{
    private int id;
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private Hall greatHall;

    private boolean standing = true;
    private boolean insideHall = false;

    public Knight(int id, Agenda agendaNew, Agenda agendaComplete, Hall greatHall){
        this.id = id;
        this.agendaNew = agendaNew;
        this.agendaComplete = agendaComplete;
        this.greatHall = greatHall;
    }
    @Override
    public void run(){

    }
    @Override
    public String toString(){
        return "Knight "+this.id;
    }



}
