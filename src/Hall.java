public class Hall {
    private Agenda agendaNew;
    private Agenda agendaComplete;
    private String tag;

    public Hall(String tag, Agenda agendaNew, Agenda agendaComplete){
        this.tag = tag;
        this.agendaComplete = agendaComplete;
        this.agendaNew = agendaNew;
    }
}
