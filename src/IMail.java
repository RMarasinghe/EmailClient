public interface IMail {
    void setSubject(String subject);
    void setBody(String body);
    String getEmailAddress();
    String getSubject();
    String getBody();
    String getDate();
}
