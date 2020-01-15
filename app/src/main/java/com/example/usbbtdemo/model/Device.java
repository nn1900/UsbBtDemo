package com.example.usbbtdemo.model;

public class Device {
  public static final String USB = "usb";
  public static final String Bluetooth = "bluetooth";

  private int id;
  private String type;
  private String name;
  private int vendorId;
  private int productId;
  private String version;

  public Device(int id, String type, String name, int vendorId, int productId, String version) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.vendorId = vendorId;
    this.productId = productId;
    this.version = version;
  }

  public int getId() { return this.id; }

  public String getType() {
    return this.type;
  }

  public String getName() {
    return this.name;
  }

  public int getVendorId() { return this.vendorId; }

  public int getProductId() { return this.productId; }

  public String getVersion() { return this.version; }
}
