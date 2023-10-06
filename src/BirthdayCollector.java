import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BirthdayCollector implements ICollector {

    private final HashMap<String, ArrayList<IRecipient>> birthdayMap;
    private static BirthdayCollector birthdayCollector;

    private BirthdayCollector() {
        this.birthdayMap = new HashMap<>();
    }

    public static BirthdayCollector getBirthdayCollector(){
        if (birthdayCollector == null){
            birthdayCollector = new BirthdayCollector();
        }
        return birthdayCollector;
    }
    public void addBirthday(IRecipient recip){
        
        //updating birthday list if recipient has a birthday
        String recipientType = recip.getRecipientType();
        if ((Objects.equals(recipientType, "Office_friend")) || (Objects.equals(recipientType, "Personal"))){
            //procedure to update the birthdayMap hash map
            String bd = recip.getBirthday().substring(5);
            if (birthdayMap.containsKey(bd)){
                birthdayMap.get(bd).add(recip);
            }
            else {
                ArrayList<IRecipient> newbdList = new ArrayList<>();
                newbdList.add(recip);
                birthdayMap.put(bd,newbdList);
            }
        }
    }

    @Override
    public Iterator getIterator() {
        return null;
    }

    @Override
    public Iterator getIterator(String birthday) {
        return new BirthdayIterator(birthdayMap.get(birthday));
    }
    
    private class BirthdayIterator implements Iterator{
        
        private int index;
        private final ArrayList<IRecipient> birthdayList;
        
        //constructor
        public BirthdayIterator(ArrayList<IRecipient> birthdayList){
            this.index=0;
            this.birthdayList=birthdayList;
        }

        @Override
        public boolean hasNext() {
            return index < birthdayList.size();
        }

        @Override
        public Object next() {
            if (this.hasNext()){
                return birthdayList.get(index++);
            }
            return null;
        }

        @Override
        public void reset() {
            this.index=0;
        }
    }
}
