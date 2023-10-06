// Index Number: 200382A
// Name: MARASINGHE M.M.R.S.

//import libraries
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import javax.mail.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Email_Client {

    public static void main(String[] args) {

//===================Initializing the Email client=================================================================================

        Date dateObj = Date.getDateInstance();
        MailCollector mailCollector = Serializer.deserialize();  // to store sent mails
        RecipientFactory recipientFactory = RecipientFactory.getRecipientFactory();
        RecipientCollector recipientCollector = RecipientCollector.getRecipientCollector();  // to store all the recipients
        BirthdayCollector birthdayCollector = BirthdayCollector.getBirthdayCollector();  // to store all recipients with birthdays with birthday as the key
        SendEmailTLS mailSender = SendEmailTLS.getEmailSender("dumbledor516@gmail.com","ejglutxskjrgeblt");

//====================Load the recipients from the file=============================================================================

        //Reading the recipient list file and loading recipients to the email client. **Note:Change the filename accordingly
        try {
            File recipientFile = new File("clientList.txt");
            Scanner fileReader = new Scanner(recipientFile);
            while (fileReader.hasNextLine()) {
                String recipientDetail = fileReader.nextLine();
                if (Objects.equals(recipientDetail, "")) continue;
                IRecipient recip = recipientFactory.createRecipient(recipientDetail); // making recipient objects
                recipientCollector.addRecipient(recip);  // storing all the recipient objects
                birthdayCollector.addBirthday(recip);  // maintaining recipients according to birthday in a hash map.
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File with the recipient list does not exist.");
        } catch (NullPointerException ignored){

        }

//==================================== Sending Birthday greetings for the day ==============================================================================================

        String today = dateObj.getDate().substring(5);  // get month and date from today's date

        // sending birthday greetings. Nothing happens if there are no entries for the given date.
        try {
            for (Iterator iterator = birthdayCollector.getIterator(today); iterator.hasNext(); ) {
                IRecipient bdRecipient = (IRecipient) iterator.next();
                BirthdayGreeting birthdayGreeting = new BirthdayGreeting(bdRecipient, dateObj);
                Mail greetingMail = birthdayGreeting.greet();
                mailSender.sendMail(greetingMail);
                mailCollector.addMail(greetingMail);

            }
        }catch (NullPointerException ignored){

        }catch (MessagingException e){
            System.out.println("Invalid recipient detail in the ClientList.txt");
        }
//==================================================================================================================================
        boolean runContinues = true;

        // run continuously until 6 is given as the input
        while (runContinues) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n"
                    +"6 - Close the Email client");

            try {
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        // input format - Official: nimal,nimal@gmail.com,ceo
                        // Use a single input to get all the details of a recipient
                        // code to add a new recipient
                        // store details in clientList.txt file
                        // Hint: use methods for reading and writing files

                        // taking input from the user: recipient detail in single line
                        System.out.print("Enter Recipient Detail: ");
                        String recipientDetail = scanner.nextLine();

                        // creating recipient object and adding to the collectors.
                        try {
                            IRecipient recipient = recipientFactory.createRecipient(recipientDetail);
                            recipientCollector.addRecipient(recipient);
                            birthdayCollector.addBirthday(recipient);

                            //adding new recipient to the clientList.txt file

                            File file = new File("clientList.txt");
                            FileWriter writer = new FileWriter(file, true);
                            BufferedWriter fileWriter = new BufferedWriter(writer);
                            if (file.length() != 0) {
                                fileWriter.newLine();
                            }
                            fileWriter.append(recipientDetail);
                            fileWriter.close();
                            writer.close();

                        } catch (IOException e) {
                            System.out.println("Cannot add a new recipient to the file clientList.txt\n");

                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Invalid input.Not enough parameters in the Recipient detail.");
                        } catch (NullPointerException ignored) {

                        }
                        break;
                    case 2:
                        // input format - email, subject, content
                        // code to send an email

                        System.out.println("Input in the format: email, subject, content");
                        String receiverString = scanner.nextLine();
                        String[] receiver = receiverString.split(",");
                        try {
                            String email = receiver[0].strip();
                            String subject = receiver[1].strip();
                            String content = receiver[2].strip();

                            IMail mail = new Mail(email, subject, content, dateObj);
                            mailSender.sendMail(mail);
                            mailCollector.addMail(mail);
                            System.out.println("Sent");

                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Not enough parameters.Input should be in the form: Email,Subject,Content");
                        } catch (MessagingException e) {
                            System.out.println("Invalid Email Address");
                        }

                        break;
                    case 3:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print recipients who have birthdays on the given date
                        System.out.println("Enter the date:");
                        String input = scanner.nextLine();
                        if (input.matches("\\d{4}/\\d{2}/\\d{2}")) {
                            String birthday = input.substring(5);
                            try {
                                for (Iterator iterator = birthdayCollector.getIterator(birthday); iterator.hasNext(); ) {
                                    IRecipient bdRecipient = (IRecipient) iterator.next();
                                    if (bdRecipient.getBirthday().startsWith(input.substring(0, 4))) {
                                        System.out.println("\n" + bdRecipient.getName() + "   " + bdRecipient.getDesignation() + "   " + bdRecipient.getEmail());
                                    }
                                }
                            } catch (NullPointerException e) {
                                System.out.println("No Recipients who have birthday on " + input);
                            }
                        } else System.out.println("Invalid date format. Accepted format is YYYY/MM/DD");
                        break;
                    case 4:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print the details of all the emails sent on the input date
                        System.out.println("Enter the date: ");
                        String inDate = scanner.nextLine();
                        if (inDate.matches("\\d{4}/\\d{2}/\\d{2}")) {
                            try {
                                for (Iterator iter = mailCollector.getIterator(); iter.hasNext(); ) {
                                    IMail mail1 = (IMail) iter.next();
                                    if (Objects.equals(mail1.getDate(), inDate)) {
                                        System.out.println("\nRecipient Address: " + mail1.getEmailAddress()
                                                + "\nSubject: " + mail1.getSubject()
                                                + "\nBody: " + mail1.getBody());
                                    }
                                }
                            } catch (NullPointerException e) {
                                System.out.println("No Result");
                            }
                        } else System.out.println("Invalid date format. Accepted format is YYYY/MM/DD");

                        break;
                    case 5:
                        // code to print the number of recipient objects in the application

                        System.out.println("Number of Recipient objects: " + recipientCollector.getCount());
                        break;
                    case 6:
                        // code to exit program

                        System.out.println("Closing Email Client.");
                        Serializer.serialize(mailCollector);
                        runContinues = false;
                        break;

                }
            } catch (InputMismatchException e){
                System.out.println("Your Input should be an integer from 1 to 6.");
            }
        }

        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes


    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)

//==========================================SendEmailTLS==============================================================================


class SendEmailTLS {

    private static SendEmailTLS emailSender;
    final String username;
    final String password;

    private SendEmailTLS(String username,String password){
        this.username=username;
        this.password=password;
    }

    public static SendEmailTLS getEmailSender(String username, String password){
        if (emailSender == null){
            emailSender = new SendEmailTLS(username, password);
        }
        return emailSender;
    }

    public void sendMail(IMail mail) throws MessagingException{
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("dumbledor516@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(mail.getEmailAddress())
        );
        message.setSubject(mail.getSubject());
        message.setText(mail.getBody());

        Transport.send(message);



    }
}

//==========================================================Serializer==============================================================

// To serialize and Deserialize

class Serializer {

    public static void serialize(MailCollector mailCollector){
        try {
            FileOutputStream fileOut = new FileOutputStream("SentMails.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(mailCollector);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Error occurred in serialization");
        }
    }

    // returns the deserialized mailcollector object if a ser file exist. Returns a new mailcollector otherwise.
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

//===========================================BirthdayGreeting==========================================================================

// to create birthday greeting depending on the recipient type.

class BirthdayGreeting {

    private final IWish wishType;
    private final IRecipient recipient;
    private final Date date;

    public BirthdayGreeting(IRecipient recipient, Date date){
        this.recipient=recipient;
        this.date=date;
        if(Objects.equals(recipient.getRecipientType(), "Office_friend")){
            this.wishType=new OfficeFriendWish();
        }
        else if (Objects.equals(recipient.getRecipientType(), "Personal")){
            this.wishType=new PersonalWish();
        }
        else
            this.wishType=null;
    }

    public Mail greet(){
        return wishType.makeWish(recipient,date);
    }
}

//================================================Wishes============================================================================

interface IWish {
    Mail makeWish(IRecipient recipient, Date date);

}

class OfficeFriendWish implements IWish {

    @Override
    public Mail makeWish(IRecipient recipient, Date date) {
        return new Mail(recipient.getEmail(),"","Wish you a Happy Birthday.Ravindu",date);
    }
}

class PersonalWish implements IWish {

    @Override
    public Mail makeWish(IRecipient recipient, Date date) {
        return new Mail(recipient.getEmail(),"","Hugs and love on your birthday.Ravindu",date);
    }

}


//==================================================Mail===========================================================================

interface IMail {

    String getEmailAddress();
    String getSubject();
    String getBody();
    String getDate();
}

// A mail object is created for each email sent : serializable
class Mail implements IMail,java.io.Serializable{

    private final String recipientAddress;
    private String subject;
    private String body;
    private final String date;

    public Mail(String recipientAddress,String subject, String body, Date date){
        this.recipientAddress=recipientAddress;
        this.subject=subject;
        this.body=body;
        this.date=date.getDate();
    }


    @Override
    public String getEmailAddress() {
        return this.recipientAddress;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public String getDate() {
        return this.date;
    }
}

//========================================================Iterator===============================================================

interface Iterator {

    boolean hasNext(); // check if there's a next object
    Object next(); // return next object
    void reset(); // to make index 0
}

//=======================================================Collectors================================================================

interface ICollector {

    Iterator getIterator();
    Iterator getIterator(String birthday);
}


// to store mail objects : Serializable
class MailCollector implements ICollector,java.io.Serializable{

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

// save recipients with birthdays in a hashmap with birthday (month and date) as the key

class BirthdayCollector implements ICollector {

    private final HashMap<String, ArrayList<IRecipient>> birthdayMap;
    private static BirthdayCollector birthdayCollector;

    // constructor
    private BirthdayCollector() {
        this.birthdayMap = new HashMap<>();
    }

    // public method to call constructor exactly one time
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


class RecipientCollector implements ICollector {

    private final ArrayList<IRecipient> recipientList;
    private static RecipientCollector recipientCollector;

    //constructor
    private RecipientCollector(){
        recipientList= new ArrayList<>();
    }

    // public method to call constructor exactly one time
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

//===========================================Recipient Factory==========================================================================

// create relevant recipient object according to the detail given
class RecipientFactory {
    private static RecipientFactory recipientFactory;

    // constructor
    private RecipientFactory(){}

    // public method to call constructor exactly one time
    public static RecipientFactory getRecipientFactory(){
        if (recipientFactory == null){
            recipientFactory = new RecipientFactory();
        }
        return recipientFactory;
    }

    // method to create recipients
    public IRecipient createRecipient(String detail){
        String[] detailArr;
        String rDet;

        if (detail.startsWith("Official:")){
            rDet = detail.substring(10);
            detailArr = rDet.split(",");
            return new Official(detailArr[0],detailArr[1],detailArr[2]);
        }
        else if (detail.startsWith("Office_friend:")){
            rDet = detail.substring(15);
            detailArr = rDet.split(",");
            return new Office_friend(detailArr[0],detailArr[1],detailArr[2],detailArr[3]);
        }
        else if (detail.startsWith("Personal:")){
            rDet = detail.substring(10);
            detailArr = rDet.split(",");
            return new Personal(detailArr[0],detailArr[2],detailArr[1],detailArr[3]);
        }
        else {
            System.out.println("Invalid recipient detail -> "+detail);
            System.out.println("Recipient type should be one of the following,\nOfficial:\nOffice_friend:\nPersonal:");
            return null;
        }
    }
}

//===============================================Recipient=============================================================

interface IRecipient{
    String getName();
    String getEmail();
    String getDesignation();
    void setDesignation(String designation);
    String getBirthday();
    void setRecipientType(String recipientType);
    String getRecipientType();
}

class Recipient implements IRecipient {
    protected String name;
    protected String email;
    protected String recipientType;
    protected String designation;

    public Recipient(String name,String email){
        this.name=name;
        this.email=email;
    }
    @Override
    public String getEmail(){
        return this.email;
    }

    @Override
    public String getDesignation() {
        return designation;
    }

    @Override
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String getBirthday() {
        return null;
    }

    @Override
    public void setRecipientType(String recipientType) {
        this.recipientType=recipientType;
    }

    @Override
    public String getRecipientType() {
        return this.recipientType;
    }

    @Override
    public String getName() {
        return this.name;
    }

}

class Official extends Recipient implements IRecipient{

    public Official(String name,String email,String designation){
        super(name,email);
        setDesignation(designation);
        setRecipientType("Official");
    }

    @Override
    public String getDesignation() {
        return this.designation;
    }

    @Override
    public String getBirthday() {
        return null;
    }

}

class Office_friend extends Official implements IRecipient{
    private String birthday;

    public Office_friend(String name, String email, String designation, String birthday) {
        super(name, email, designation);
        this.birthday=birthday;
        setDesignation(designation);
        setRecipientType("Office_friend");
    }

    @Override
    public String getBirthday() {
        return this.birthday;
    }
}

class Personal extends Recipient{
    private final String birthday;

    public Personal(String name, String email, String nick_name, String birthday) {
        super(name, email);
        this.birthday=birthday;
        setDesignation(nick_name);
        setRecipientType("Personal");
    }

    @Override
    public String getBirthday() {
        return this.birthday;
    }
}

//============================================Date===============================================================================

class Date {

    private static Date dateInstance;
    private final String date;

    // constructor
    private Date(){
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    // public method to call constructor exactly one time
    public static Date getDateInstance() {
        if (dateInstance == null){
            dateInstance = new Date();
        }
        return dateInstance;
    }

    public String getDate() {
        return date;
    }
}
