import java.util.Objects;

public class BirthdayGreeting {

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

//create an object of this class with the wish type needed and call greet() function to make the wish