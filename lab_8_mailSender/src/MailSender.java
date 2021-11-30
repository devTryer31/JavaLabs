import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailSender {

    static void sendMessage(String from,
                            String from_password,
                            String to,
                            String subject,
                            String msg) throws MessagingException
    {
        final String host = "smtp.mail.ru";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "25");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, from_password);
                    }
                });


        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        // actual mail body
        message.setText(msg);

        Transport.send(message);
    }

}
