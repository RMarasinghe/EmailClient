public class Official extends Recipient implements IRecipient{

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
