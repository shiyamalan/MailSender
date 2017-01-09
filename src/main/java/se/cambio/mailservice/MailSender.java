package se.cambio.mailservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import se.cambio.mailservice.file.MailPropertyReader;

/**
 * Hello world!
 *
 */
public class MailSender
{

  private static MailSender instance = null;

  private MailSender()
  {

  }

  public static MailSender getInstance()
  {
    if (instance == null)
      instance = new MailSender();
    return instance;
  }

  public static void main(String[] args)
  {
    try
    {
      MailBody mailBody = new MailBody();
      String line1 = "FileLocation1" + MailBody.TEXT_SPLITER + "StackOverFlow" + MailBody.TEXT_SPLITER
          + "1> 2016.08.05 09:25:07 > [AWT-EventQueue-1] INFO  - USER=Lakare12      SERVER=jnp://CSLK-QA-R81S:25099"
          + MailBody.TEXT_SPLITER + "3";
      String line2 = "FileLocation1" + MailBody.TEXT_SPLITER +"NullPointerException" + MailBody.TEXT_SPLITER
          + "Line1<br/>Line2" 
          + MailBody.TEXT_SPLITER + "3";
      
      String line3 = "FileLocation1" + MailBody.TEXT_SPLITER + "Memory Out of Bound Exception" + MailBody.TEXT_SPLITER
          + "1> 2016.08.05 09:25:07 > [AWT-EventQueue-1] INFO  - USER=Lakare12      SERVER=jnp://CSLK-QA-R81S:25099"
          + MailBody.TEXT_SPLITER + "3";
      
      ArrayList<String> arrayList = new ArrayList<String>();
      arrayList.add(line1);
      arrayList.add(line2);
      arrayList.add(line3);
      mailBody.msg_body_lines = arrayList;
      MailSender.getInstance().sendMail(mailBody);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void sendMail(MailBody contents) throws IOException
  {
    MailContentManager mailContentManager = new MailContentManager();
    Properties properties = MailPropertyReader.getInstance().getMailProperties();
    Session session = Session.getDefaultInstance(properties);

    try
    {
      MimeMessage message = mailContentManager.getMimeMessage(session, contents);
      message.setContent(mailContentManager.getMessageBodyPart(contents));
      Transport.send(message);
      System.out.println("Sent message successfully....");
    }
    catch (MessagingException mex)
    {
      mex.printStackTrace();
    }
  }

  @SuppressWarnings("unused")
  private Session getSession(final Properties props)
  {
    Session session = Session.getInstance(props, new javax.mail.Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        return new PasswordAuthentication(props.get("mail.smtp.user").toString(),
            props.get("mail.smtp.password").toString());
      }
    });
    return session;
  }

}
