public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;
    private boolean kingInside;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;
        this.kingInside = false;
    }

    synchronized void kingArrive(String name) {
        this.kingInside = true;
        System.out.println(name+"enters"+this.toString());
        this.notifyAll();
    }

    synchronized void kingLeaves(String name){
        this.kingInside = false;
        System.out.println(name+"exists"+this.toString());
        this.notifyAll();
    }

    @Override
    public String toString(){
        return " "+this.tag;
    }
}
