public class Mail implements IMail,java.io.Serializable{

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
    public void setSubject(String subject) {
        this.subject=subject;
    }

    @Override
    public void setBody(String body) {
        this.body=body;

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
