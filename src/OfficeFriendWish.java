public class OfficeFriendWish implements IWish {


    @Override
    public Mail makeWish(IRecipient recipient, Date date) {
        return new Mail(recipient.getEmail(),"","Wish you a Happy Birthday.Ravindu",date);
    }
}
