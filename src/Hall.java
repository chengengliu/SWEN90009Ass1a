public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;
    }

    synchronized void arrive(Knight knight) throws InterruptedException{

    }

    synchronized void arrive(KingArthur kingArthur)throws InterruptedException{

    }

    synchronized void leave(Knight knight)throws InterruptedException{

    }

    synchronized void leave(KingArthur kingArthur) throws InterruptedException{

    }

    synchronized void startMeeting() throws InterruptedException{

    }
}
