
import com.sun.mail.smtp.SMTPAddressFailedException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendEmailTLS {

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
//recipientAddress, subject, body