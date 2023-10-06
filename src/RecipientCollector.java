import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RecipientCollector implements ICollector {

    private final ArrayList<IRecipient> recipientList;
    private static RecipientCollector recipientCollector;

    //constructor
    private RecipientCollector(){
        recipientList= new ArrayList<>();
    }

    public static RecipientCollector getRecipientCollector(){
        if (recipientCollector == null){
            recipientCollector = new RecipientCollector();
        }
        return recipientCollector;
    }

    //Method to add recipients to collectors
    public void addRecipient(IRecipient recip){

        //updating recipient list
        recipientList.add(recip);
    }
    public int getCount(){
        return recipientList.size();
    }

    @Override
    public Iterator getIterator() {
        return new RecipientIterator();
    }

    @Override
    public Iterator getIterator(String birthday) {
        return null;
    }


    private class RecipientIterator implements Iterator{

        private int index;


        @Override
        public boolean hasNext() {
            return index < recipientList.size();
        }

        @Override
        public Object next() {
            if (this.hasNext()){
                return recipientList.get(index++);
            }
            return null;
        }

        @Override
        public void reset() {
            this.index=0;
        }
    }

}
// add iterator class inside the collector class(nested classes)
// try to use hash map instead of birthdaylist. key=birthday,key points to an arraylist of recipient objects who have birthday on that day
