import java.util.ArrayList;

public class MailCollector implements ICollector,java.io.Serializable{

    private final ArrayList<IMail> sentMails;

    //constructor
    public MailCollector() {
        this.sentMails = new ArrayList<>();
    }

    public void addMail(IMail mail){
        sentMails.add(mail);
    }

    @Override
    public Iterator getIterator() {
        return new MailIterator();
    }

    @Override
    public Iterator getIterator(String birthday) {
        return null;
    }

    private class MailIterator implements Iterator{

        private int index;

        @Override
        public boolean hasNext() {
            return index < sentMails.size();
        }

        @Override
        public Object next() {
            if (this.hasNext()){
                return sentMails.get(index++);
            }
            return null;
        }

        @Override
        public void reset() {
            this.index=0;

        }
    }
}
