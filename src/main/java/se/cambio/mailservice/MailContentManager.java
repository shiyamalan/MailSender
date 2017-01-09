package se.cambio.mailservice;

import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import se.cambio.mailservice.formats.TableFormater;

public class MailContentManager
{

  private static final String MESSAGE_FORMAT = "text/html";

  public MimeMessage getMimeMessage(Session session, MailBody contents) throws AddressException, MessagingException
  {
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(contents.from));
    message.setRecipients(Message.RecipientType.TO, getRecipient(contents.to));
    message.setSubject(contents.msg_subject);
    message.setSentDate(new Date());
    message.setContent(message, MESSAGE_FORMAT);

    return message;
  }

  public Multipart getMessageBodyPart(MailBody contents) throws MessagingException
  {
    BodyPart messageBodyPart = attachBodySection(contents);
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(messageBodyPart);
    if (contents.is_item_attched)
    {
      messageBodyPart = new MimeBodyPart();
      String filename = contents.attachment_path;
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      multipart.addBodyPart(messageBodyPart);
    }

    return multipart;
  }

  private BodyPart attachBodySection(MailBody contents) throws MessagingException
  {
    BodyPart messageBodyPart = new MimeBodyPart();
    TableFormater formater = new TableFormater();
    formater.setTableColumn(contents.msg_body_lines.get(0).split(MailBody.TEXT_SPLITER).length);
    String tableMessage = TableFormater.getTableHeader();
    for (String line : contents.msg_body_lines)
    {

      tableMessage += TableFormater.getMessageBodayAsTableRow(line, TableFormater.getTableColumn());

    }
    tableMessage += (TableFormater.TABLE_END_TAG);

    messageBodyPart.setContent(tableMessage.toString(), MESSAGE_FORMAT);
    return messageBodyPart;
  }

  private InternetAddress[] getRecipient(String to) throws AddressException
  {
    String recipient = to;
    String[] recipientList = recipient.split(",");
    InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
    int counter = 0;
    for (String rec : recipientList)
    {
      recipientAddress[counter] = new InternetAddress(rec.trim());
      counter++;
    }
    //message.setRecipients(Message.RecipientType.TO, recipientAddress);

    return recipientAddress;
  }
}
