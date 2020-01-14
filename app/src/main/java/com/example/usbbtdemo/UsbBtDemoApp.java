package com.example.usbbtdemo;

import android.app.Application;
import android.content.Context;
import android.hardware.usb.UsbManager;

import cn.wch.ch34xuartdriver.CH34xUARTDriver;

public class UsbBtDemoApp extends Application {
  public static CH34xUARTDriver driver;
  private static final String ACTION_USB_PERMISSION = "cn.wch.wchusbdriver.USB_PERMISSION";

  @Override
  public void onCreate() {
    super.onCreate();
    Context context = this.getApplicationContext();
    UsbManager usbManager = (UsbManager)context.getSystemService(Context.USB_SERVICE);
    driver = new CH34xUARTDriver(usbManager, context, ACTION_USB_PERMISSION);
  }
}
