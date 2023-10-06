import java.io.*;

public class Serializer {

    public static void serialize(MailCollector mailCollector){
        try {
            FileOutputStream fileOut = new FileOutputStream("SentMails.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mailCollector);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Error occurred in serialization");
            i.printStackTrace();
        }
    }

    public static MailCollector deserialize(){
        try {
            FileInputStream fileIn = new FileInputStream("SentMails.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            MailCollector mailCollector = (MailCollector) in.readObject();
            in.close();
            fileIn.close();
            return mailCollector;
        } catch (IOException i) {
            return new MailCollector();
        } catch (ClassNotFoundException c) {
            System.out.println("MailCollector class not found");
            return new MailCollector();
        }

    }
}
