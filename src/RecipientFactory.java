
public class RecipientFactory {
    private static RecipientFactory recipientFactory;

    private RecipientFactory(){}

    public static RecipientFactory getRecipientFactory(){
        if (recipientFactory == null){
            recipientFactory = new RecipientFactory();
        }
        return recipientFactory;
    }
    public IRecipient createRecipient(String detail){
        String[] detailArr;
        String rDet;

        if (detail.startsWith("Official:")){
            rDet = detail.substring(10);
            detailArr = rDet.split(",");
            return new Official(detailArr[0],detailArr[1],detailArr[2]);
        }
        else if (detail.startsWith("Office_friend:")){
            rDet = detail.substring(15);
            detailArr = rDet.split(",");
            return new Office_friend(detailArr[0],detailArr[1],detailArr[2],detailArr[3]);
        }
        else if (detail.startsWith("Personal:")){
            rDet = detail.substring(10);
            detailArr = rDet.split(",");
            return new Personal(detailArr[0],detailArr[2],detailArr[1],detailArr[3]);
        }
        else {
            System.out.println("Invalid recipient detail -> "+detail);
            System.out.println("Recipient type should be one of the following,\nOfficial:\nOffice_friend:\nPersonal:");
            return null;
        }
    }
}


