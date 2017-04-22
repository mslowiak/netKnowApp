package netKnow;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    final String HOST = "smtp.gmail.com";
    final int PORT = 587;
    final String FROM = "netknowapp@gmail.com";
    final String USERNAME = "netknowapp";
    final String PASSWORD = "netknowapp123";
    String recipient = "";
    String messageSubject = "Rejestracja konta dla aplikacji netKnow";
    String messageContent = "";

    public MailSender(String recipient, String messageContent){
        this.recipient = recipient;
        this.messageContent = messageContent;
    }

    public void sendMail() throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", true);

        Session mailSession = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(FROM));
        message.setSubject(messageSubject);
        message.setContent(messageContent, "text/plain; charset=ISO-8859-2");
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", USERNAME, PASSWORD);
        System.out.println("Transport: " + transport.toString());

        transport.sendMessage(message, message.getAllRecipients());
    }
}
