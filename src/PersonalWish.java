public class PersonalWish implements IWish {

    @Override
    public Mail makeWish(IRecipient recipient, Date date) {
        return new Mail(recipient.getEmail(),"","Hugs and love on your birthday.Ravindu",date);
    }

}

