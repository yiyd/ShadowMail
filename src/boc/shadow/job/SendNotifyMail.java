package boc.shadow.job;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * Created by Tong on 2015/8/21.
 * USE JAVA mail
 */
public class SendNotifyMail {
    private String hostName = "smtp.yeah.net";// notes server address
    private String userName = "xxx@yeah.net";// admin_user`s email address
    private String userPasswd = "xxxx";// admin_user passwd
    private String from = "xxxx@yeah.net"; // admin_user`s email
    private String to = "";
    private String subject = "";
    private String mailContent = "";

    public SendNotifyMail(String to, String subject, String mailContent) {
        this.to = to;
        this.subject = subject;
        this.mailContent = mailContent;
    }

    public SendNotifyMail() {
    }

    public void sendMail () throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", hostName);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // use session to define the message object
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            // set the mail title
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);

            // Add the mail content
            MimeMultipart multipart = new MimeMultipart();
            // add context
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText(mailContent);
            multipart.addBodyPart(bodyPart);

            mimeMessage.setContent(multipart);
            mimeMessage.saveChanges();

            // send the email
            Transport transport = session.getTransport("smtp");
            transport.connect(hostName, userName, userPasswd);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
