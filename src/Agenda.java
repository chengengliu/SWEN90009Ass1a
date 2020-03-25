/**
 *  Handle the new coming in quest (from the producer)
 *  and giving out the quest (to the consumer).
 *  Also handle the quest release and quest acquire
 */
public class Agenda {

    private Quest quest;
    private String tag = null;

    public Agenda(String tag){
        this.tag = tag;
        this.quest = null;
    }

//    添加新的Quest from producer
    synchronized void addNew(Quest quest) throws InterruptedException{
        // Wait until there is on quest coming in, from the producer.
        while(this.quest!=null){
            this.wait();
        }
        this.quest = quest;
        System.out.println(this.quest.toString() + this.toString());
        this.notifyAll();
    }
//  移除已经完成的Quest
    synchronized void removeComplete() throws InterruptedException{
        // If current agenda holds non, wait until it has one to be removed.
        while(this.quest==null){
            this.wait();
        }
        Quest questTemp = this.quest;
        this.quest = null;
        System.out.println(questTemp.toString()+ this.toString());
        this.notifyAll();
    }



    @Override
    public String toString(){
        return " added to "+this.tag;

    }







}
