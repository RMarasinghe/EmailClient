public class Recipient implements IRecipient {
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
