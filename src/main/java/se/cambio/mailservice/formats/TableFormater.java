package se.cambio.mailservice.formats;

import se.cambio.mailservice.MailBody;

public class TableFormater
{
  public static final String TABLE_TAG = "<table  border=1>";

  public static final String TABLE_END_TAG = "</table>";

  public static final String TABLE_ROW_TAG = "<tr>";

  public static final String TABLE_ROW_END_TAG = "</tr>";

  public static final String ROW_DATA_TAG = "<td>";

  public static final String ROW_DATA_END_TAG = "</td>";

  public static final String TABLE_HEADER_TAG = "<th>";

  public static final String TABLE_HEADER_END_TAG = "</th>";

  public static String columns_I[] = { "Error Name", "Error Description", "No of Time Error Occured" };
  
  public static String columns_H[] = {"File Location"};
  private static int table_column_size = 3;

  public static String getTableHeader()
  {
    //<table border=1>
    String table_header = TABLE_TAG +  TABLE_ROW_TAG;
    String columns[] = null;
    if(getTableColumn()==3)
      columns = columns_I;
    else
     columns = concat(columns_H, columns_I);
    for (String column : columns)
    {
      table_header += TABLE_HEADER_TAG + column + TABLE_HEADER_END_TAG;
    }
    table_header += TABLE_ROW_END_TAG;
    return table_header;
  }

  public static String getMessageBodayAsTableRow(String text, int columnSize)
  {
    String result = "";
    String components[] = text.split(MailBody.TEXT_SPLITER);

    if (components != null)
    {
      result = TABLE_ROW_TAG;
      for (int i = 0; i < columnSize; i++)
        result += ROW_DATA_TAG + components[i] + ROW_DATA_END_TAG;
      result += TABLE_ROW_END_TAG;
    }

    return result;
  }
  private static String[] concat(String[] a, String[] b) {
    int aLen = a.length;
    int bLen = b.length;
    String[] c= new String[aLen+bLen];
    System.arraycopy(a, 0, c, 0, aLen);
    System.arraycopy(b, 0, c, aLen, bLen);
    return c;
 }
  public void setTableColumn(int number)
  {
    if (number > 0)
    {
      table_column_size = number;
      return;
    }
    table_column_size = 3;
  }
  public static int getTableColumn()
  {
    return table_column_size;
  }
}
