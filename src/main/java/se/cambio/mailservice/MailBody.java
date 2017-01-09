package se.cambio.mailservice;

import java.util.ArrayList;
import java.util.List;

public class MailBody
{
  public String from = "Srishiyamalan.Ratnavel@gmail.com";

  public String to = "Srishiyamalan.Ratnavel@cambio.lk";

  public String msg_subject = "Sample Subject";

  public String msg_body_string = "Sample Body";

  public String attachment_path = "E:\\Music\\songs\\01 Aambalaikum Pombalaikum.mp3";

  public List<String> msg_body_lines;

  public boolean is_item_attched = false;

  public static final String TEXT_SPLITER = "####";

  public MailBody()
  {
    msg_body_lines = new ArrayList<String>();
  }

  
  

}
