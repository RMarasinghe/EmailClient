public class Personal extends Recipient{
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
