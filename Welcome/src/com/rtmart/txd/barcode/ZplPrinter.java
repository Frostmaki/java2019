package com.rtmart.txd.barcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocFlavor.BYTE_ARRAY;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JOptionPane;

public class ZplPrinter
{
  private String printerURI = null;
  private PrintService printService = null;
  private byte[] dotFont;
  private String begin = "^XA";
  private String end = "^XZ";
  private String content = "";
  private static String PrtName = "rail4";
  
  public ZplPrinter()
  {
    this.printerURI = PrtName;
    
    System.out.println(getClass().getResource("/").getPath() + "../lib/ts24.lib");
    File file = new File(getClass().getResource("/").getPath() + "../lib/ts24.lib");
    if (file.exists()) {
      try
      {
        FileInputStream fis = new FileInputStream(file);
        this.dotFont = new byte[fis.available()];
        fis.read(this.dotFont);
        fis.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    } else {
      System.out.println(getClass().getResource("/").getPath() + "../lib/ts24.lib文件不存在");
    }
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
    PrintService[] arrayOfPrintService1;
    int j;
    int i;
    if ((services != null) && (services.length > 0))
    {
      j = (arrayOfPrintService1 = services).length;
      for (i = 0; i < j; i++)
      {
        PrintService service = arrayOfPrintService1[i];
        if (service.getName().contains(this.printerURI))
        {
          this.printService = service;
          break;
        }
      }
    }
    if (this.printService == null)
    {
      System.out.println("没有找到打印机[" + this.printerURI + "]");
      JOptionPane.showMessageDialog(null, "找不到" + PrtName + "打印机!");
      if ((services != null) && (services.length > 0))
      {
        System.out.println("可用的打印机列表");
        j = (arrayOfPrintService1 = services).length;
        for (i = 0; i < j; i++)
        {
          PrintService service = arrayOfPrintService1[i];
          System.out.println("[" + service.getName() + "]");
        }
      }
    }
    else
    {
      System.out.println("找到打印机[" + this.printerURI + "]");
      System.out.println("打印机名称[" + ((PrinterName)this.printService.getAttribute(PrinterName.class)).getValue() + "]");
    }
  }
  
  public ZplPrinter(String printerURI)
  {
    this.printerURI = printerURI;
    
    System.out.println(getClass().getResource("/").getPath() + "../lib/ts24.lib");
    File file = new File(getClass().getResource("/").getPath() + "../lib/ts24.lib");
    if (file.exists()) {
      try
      {
        FileInputStream fis = new FileInputStream(file);
        this.dotFont = new byte[fis.available()];
        fis.read(this.dotFont);
        fis.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    } else {
      System.out.println(getClass().getResource("/").getPath() + "../lib/ts24.lib文件不存在");
    }
    PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
    PrintService[] arrayOfPrintService1;
    int j;
    int i;
    if ((services != null) && (services.length > 0))
    {
      j = (arrayOfPrintService1 = services).length;
      for (i = 0; i < j; i++)
      {
        PrintService service = arrayOfPrintService1[i];
        if (printerURI.equals(service.getName()))
        {
          this.printService = service;
          break;
        }
      }
    }
    if (this.printService == null)
    {
      System.out.println("没有找到打印机[" + printerURI + "]");
      if ((services != null) && (services.length > 0))
      {
        System.out.println("可用的打印机列表");
        j = (arrayOfPrintService1 = services).length;
        for (i = 0; i < j; i++)
        {
          PrintService service = arrayOfPrintService1[i];
          System.out.println("[" + service.getName() + "]");
        }
      }
    }
    else
    {
      System.out.println("找到打印机[" + printerURI + "]");
      System.out.println("打印机名称[" + ((PrinterName)this.printService.getAttribute(PrinterName.class)).getValue() + "]");
    }
  }
  
  public void setBarcode(String barcode, String zpl)
  {
    this.content += zpl.replace("${data}", barcode);
  }
  
  public void setText(String str, int x, int y, int eh, int ew, int es, int mx, int my, int ms)
  {
    byte[] ch = str2bytes(str);
    for (int off = 0; off < ch.length;) {
      if ((ch[off] & 0xFF) >= 160)
      {
        int qcode = ch[off] & 0xFF;
        int wcode = ch[(off + 1)] & 0xFF;
        this.content += String.format("^FO%d,%d^XG0000%01X%01X,%d,%d^FS\n", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(qcode), Integer.valueOf(wcode), Integer.valueOf(mx), Integer.valueOf(my) });
        this.begin += String.format("~DG0000%02X%02X,00072,003,\n", new Object[] { Integer.valueOf(qcode), Integer.valueOf(wcode) });
        qcode = qcode + 128 - 32 & 0xFF;
        wcode = wcode + 128 - 32 & 0xFF;
        int offset = (qcode - 16) * 94 * 72 + (wcode - 1) * 72;
        for (int j = 0; j < 72; j += 3)
        {
          qcode = this.dotFont[(j + offset)] & 0xFF;
          wcode = this.dotFont[(j + offset + 1)] & 0xFF;
          int qcode1 = this.dotFont[(j + offset + 2)] & 0xFF;
          this.begin += String.format("%02X%02X%02X\n", new Object[] { Integer.valueOf(qcode), Integer.valueOf(wcode), Integer.valueOf(qcode1) });
        }
        x += ms * mx;
        off += 2;
      }
      else if ((ch[off] & 0xFF) < 160)
      {
        setChar(String.format("%c", new Object[] { Byte.valueOf(ch[off]) }), x, y, eh, ew);
        x += es;
        off++;
      }
    }
  }
  
  public void setChar(String str, int x, int y, int h, int w)
  {
    this.content = (this.content + "^FO" + x + "," + y + "^A0," + h + "," + w + "^FD" + str + "^FS");
  }
  
  public void setCharR(String str, int x, int y, int h, int w)
  {
    this.content = (this.content + "^FO" + x + "," + y + "^A0R," + h + "," + w + "^FD" + str + "^FS");
  }
  
  public void resetZpl()
  {
    this.begin = "^XA";
    this.end = "^XZ";
    this.content = "";
  }
  
  public String getZpl()
  {
    return this.begin + this.content + this.end;
  }
  
  public boolean print(String zpl)
  {
    if (this.printService == null)
    {
      System.out.println("打印出错：没有找到打印机[" + this.printerURI + "]");
      return false;
    }
    DocPrintJob job = this.printService.createPrintJob();
    byte[] by = zpl.getBytes();
    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    Doc doc = new SimpleDoc(by, flavor, null);
    try
    {
      job.print(doc, null);
      System.out.println("已打印");
      return true;
    }
    catch (PrintException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  private byte[] str2bytes(String s)
  {
    if ((s == null) || ("".equals(s))) {
      return null;
    }
    byte[] abytes = null;
    try
    {
      abytes = s.getBytes("gb2312");
    }
    catch (UnsupportedEncodingException ex)
    {
      ex.printStackTrace();
    }
    return abytes;
  }
}

