// Index Number: 200382A
// Name: MARASINGHE M.M.R.S.

//import libraries
import javax.mail.MessagingException;
import java.io.*;

import java.util.Objects;
import java.util.Scanner;

public class Email_Client {

    public static void main(String[] args) {

//===================Initializing the Email client=================================================================================

        Date dateObj = Date.getDateInstance();
        MailCollector mailCollector = Serializer.deserialize();  // to store sent mails
        RecipientFactory recipientFactory = RecipientFactory.getRecipientFactory();
        RecipientCollector recipientCollector = RecipientCollector.getRecipientCollector();  // to store all the recipients
        BirthdayCollector birthdayCollector = BirthdayCollector.getBirthdayCollector();  // to store recipients with birthdays with birthday as the key
        SendEmailTLS mailSender = SendEmailTLS.getEmailSender("dumbledor516@gmail.com","ejglutxskjrgeblt");

//====================Load the recipients from the file=============================================================================

        //Reading the recipient list file and loading recipients to the email client. **Note:Change the filename accordingly
        try {
            File recipientFile = new File("C:\\Users\\ravin\\Desktop\\New folder\\clientList.txt");
            Scanner fileReader = new Scanner(recipientFile);
            while (fileReader.hasNextLine()) {
                String recipientDetail = fileReader.nextLine();
                if (Objects.equals(recipientDetail, "")) continue;
                IRecipient recip = recipientFactory.createRecipient(recipientDetail); // making recipient objects
                recipientCollector.addRecipient(recip);  // storing all the recipient objects
                birthdayCollector.addBirthday(recip);  // maintaining recipients according to birthday in a hash map.
                //note:code to store recipient objects.
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File with the recipient list does not exist.");
        } catch (NullPointerException ignored){

        }

//==================================== Sending Birthday greetings for the day ==============================================================================================

        String today = dateObj.getDate().substring(5);

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

        while (runContinues) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nEnter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n"
                    +"6 - Close the Email client");

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

                        File file = new File("C:\\Users\\ravin\\Desktop\\New folder\\clientList.txt");
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

                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid input.Not enough parameters in the Recipient detail.");
                    }catch (NullPointerException ignored){

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

                    }catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("Not enough parameters.Input should be in the form: Email,Subject,Content");
                    }catch (MessagingException e){
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
                                    System.out.println("\n"+bdRecipient.getName() + "   " + bdRecipient.getDesignation() + "   " + bdRecipient.getEmail());
                                }
                            }
                        } catch (NullPointerException e) {
                            System.out.println("No Recipients who have birthday on " + input);
                        }
                    }else System.out.println("Invalid date format. Accepted format is YYYY/MM/DD");
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
        }

        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes


    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)
