public class Office_friend extends Official implements IRecipient{
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
