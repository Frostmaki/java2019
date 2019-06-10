package com.rtmart.txd.barcode;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class Print
{
  public static String[] BarcodeType = { "WLX", "D", "GH", "DD", "", "", "DD" };
  public static String[] StrFormat = { "%06d", "%03d", "%06d", "%04d", "", "%d", "%06d" };
  public String prtBarcode;
  
  public boolean barcodePrint(int barSeq, int barType)
  {
    ZplPrinter p = new ZplPrinter();
    try
    {
      this.prtBarcode = getBarcode(barSeq, barType);
    }
    catch (UnknownHostException e)
    {
      JOptionPane.showMessageDialog(null, "获取店别失败！");
      e.printStackTrace();
    }
    String barFormat = "^FO080,060^BY2,2.0,160^BCN,,N,N,N^FD${data}^FS";
    p.setBarcode(this.prtBarcode, barFormat);
    p.setChar(this.prtBarcode, 90, 230, 48, 48);
    String zpl = p.getZpl();
    System.out.println(zpl);
    boolean result1 = p.print(zpl);
    if (result1) {
      System.out.println("打印完毕!");
    }
    p.resetZpl();
    return result1;
  }
  
  public boolean barcodePrint(String JianHuoQu, int ZouDao, int Bei, int LiaoWei)
  {
    ZplPrinter p = new ZplPrinter();
    
    this.prtBarcode = getBarcode(JianHuoQu, ZouDao, Bei, LiaoWei);
    
    String barFormat = "^FO120,060^BY2,2.0,160^BCN,,N,N,N^FD${data}^FS";
    p.setBarcode(this.prtBarcode, barFormat);
    p.setChar(this.prtBarcode, 120, 230, 48, 48);
    String zpl = p.getZpl();
    System.out.println(zpl);
    boolean result1 = p.print(zpl);
    if (result1) {
      System.out.println("打印完毕！!");
    }
    p.resetZpl();
    return result1;
  }
  
  private String getBarcode(int barSeq, int barType)
    throws UnknownHostException
  {
    String rtnBarcode;

    switch (barType)
    {
    case 5: 
      rtnBarcode = String.format(StrFormat[barType], new Object[] { Integer.valueOf(barSeq) });
      break;
    case 6: 
      rtnBarcode = BarcodeType[barType] + String.format(StrFormat[barType], new Object[] { Integer.valueOf(barSeq) });
      break;
    default: 
      rtnBarcode = getStore() + BarcodeType[barType] + String.format(StrFormat[barType], new Object[] { Integer.valueOf(barSeq) });
    }
    System.out.println(rtnBarcode);
    return rtnBarcode;
  }
  
  private String getBarcode(String JianHuoQu, int ZouDao, int Bei, int LiaoWei)
  {
    String rtnBarcode = JianHuoQu.toUpperCase() + "-" + String.format("%02d", new Object[] { Integer.valueOf(ZouDao) }) + "-" + String.format("%02d", new Object[] { Integer.valueOf(Bei) }) + "-" + String.format("%02d", new Object[] { Integer.valueOf(LiaoWei) });
    System.out.println(rtnBarcode);
    return rtnBarcode;
  }
  
  private String getStore()
    throws UnknownHostException
  {
    int storeArea = 0;
    int storeNo = 0;
    
    InetAddress ia = InetAddress.getLocalHost();
    int store;
    if ((Byte.toUnsignedInt(ia.getAddress()[0]) == 10) && (Byte.toUnsignedInt(ia.getAddress()[1]) == 10) && (Byte.toUnsignedInt(ia.getAddress()[2]) == 1))
    {
      System.out.println("ho01开发环境");
      store = 1001;
    }
    else
    {
      switch (ia.getAddress()[1])
      {
      case 16: 
      case 26: 
      case 36: 
      case 46: 
      case 56: 
        storeArea = 1;
        break;
      case 17: 
      case 27: 
      case 37: 
      case 47: 
      case 57: 
        storeArea = 2;
        break;
      case 18: 
      case 28: 
      case 38: 
      case 48: 
      case 58: 
        storeArea = 3;
        break;
      case 19: 
      case 29: 
      case 39: 
      case 49: 
      case 59: 
        storeArea = 4;
        break;
      case 20: 
      case 30: 
      case 40: 
      case 50: 
      case 60: 
        storeArea = 5;
        break;
      case 21: 
      case 22: 
      case 23: 
      case 24: 
      case 25: 
      case 31: 
      case 32: 
      case 33: 
      case 34: 
      case 35: 
      case 41: 
      case 42: 
      case 43: 
      case 44: 
      case 45: 
      case 51: 
      case 52: 
      case 53: 
      case 54: 
      case 55: 
      default: 
        storeArea = 1;
      }
      storeNo = (Byte.toUnsignedInt(ia.getAddress()[1]) - 16) / 10 * 254 + Byte.toUnsignedInt(ia.getAddress()[2]);
      store = storeArea * 1000 + storeNo;
    }
    return Integer.toString(store);
  }
}

