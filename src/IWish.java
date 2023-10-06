public interface IWish {
//    String makeWish();  // to create the customized wish
    Mail makeWish(IRecipient recipient, Date date);

}

