package interfaces.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import logger.Logger;

public class MailSender {
	
	protected Session mailSession;
	
	public void login(String smtpHost, int smtpPort, final String user, final String pw) {
		Properties props = new Properties();
		props.put("interfaces.mail.smtp.host", smtpHost);
        props.put("interfaces.mail.smtp.socketFactory.port", smtpPort);
        props.put("interfaces.mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("interfaces.mail.smtp.auth", "true");
        props.put("interfaces.mail.smtp.port", smtpPort);
        
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pw);
            }
        };

        this.mailSession = Session.getDefaultInstance(props, auth);
        Logger.log("Mail", "Info", "Logged in into the interfaces.mail sever");
	}
	
	public void sendMail(String senderMail, String senderName, String receiverAddress, String subject, String message) throws MessagingException, IllegalStateException, UnsupportedEncodingException {
        if(mailSession == null){
            throw new IllegalStateException("Du musst dich zuerst einloggen. Rufe dazu die login Methode auf");
        }

        MimeMessage msg = new MimeMessage(mailSession);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(senderMail, senderName));
        msg.setReplyTo(InternetAddress.parse(senderMail, false));
        msg.setSubject(subject, "UTF-8");
        msg.setText(message, "UTF-8");
        msg.setSentDate(new Date());

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddress, false));

        Logger.log("Mail", "Info", "send interfaces.mail...");
        send(msg);
        Logger.log("Mail", "Info", "Mail sended");
    }
	
	public void sendMail(String senderMail, String receiverAddress, String message) throws IllegalStateException, UnsupportedEncodingException, MessagingException {
		this.sendMail(senderMail, "Home", receiverAddress, "Home", message);
	}
	
	public void send(String senderMail, String receiverAddress, String subject, String message) throws IllegalStateException, UnsupportedEncodingException, MessagingException {
		this.sendMail(senderMail, "Home", receiverAddress, subject, message);
	}
	
	
	private void send(Message msg) throws MessagingException {
		Transport.send(msg);
	}

}
