package se.cambio.mailservice.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailPropertyReader
{
  public static final String propFileName = "mail.properties";

  public static MailPropertyReader instance = null;

  private MailPropertyReader()
  {

  }

  public static MailPropertyReader getInstance()
  {
    if (instance == null)
      instance = new MailPropertyReader();
    return instance;
  }

  public Properties getMailProperties() throws IOException
  {

    InputStream inputStream = null;
    Properties prop = new Properties();
    try
    {
      inputStream = this.getClass().getClassLoader().getResourceAsStream(propFileName);
      if (inputStream != null)
      {
        prop.load(inputStream);
      }
      else
      {
        throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      inputStream.close();
    }
    return prop;
  }
}
