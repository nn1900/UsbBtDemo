package com.example.usbbtdemo;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usbbtdemo.model.Device;

import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UsbDeviceActivity extends AppCompatActivity {
  private String deviceName;
  private Device deviceInfo;
  private UsbDevice usbDevice;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    deviceName = getIntent().getStringExtra("deviceName");
    if (TextUtils.isEmpty(deviceName)) {
      this.finish();
      return;
    }

    setContentView(R.layout.activity_usb_device);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setTitle(R.string.usb_device_activity_title);

    loadDeviceInfo();

    TextView deviceIdTextView = findViewById(R.id.device_id);
    TextView deviceNameTextView = findViewById(R.id.device_name);
    TextView deviceVendorIdView = findViewById(R.id.device_vendor_id);
    TextView deviceProductIdView = findViewById(R.id.device_product_id);

    deviceIdTextView.setText(String.valueOf(deviceInfo.getId()));
    deviceNameTextView.setText(deviceInfo.getName());
    deviceVendorIdView.setText(String.valueOf(deviceInfo.getVendorId()));
    deviceProductIdView.setText(String.valueOf(deviceInfo.getProductId()));

    UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
    assert null != usbManager;
    boolean hasPermission = usbManager.hasPermission(this.usbDevice);
    Toast.makeText(this, hasPermission ? "Has Permission" : "No permission", Toast.LENGTH_LONG).show();
  }

  private Device getUsbDeviceInfo(UsbDevice device) {
    String version = "";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      version = device.getVersion();
    }
    String deviceName = device.getDeviceName();
    return new Device(
      device.getDeviceId(),
      Device.USB,
      deviceName,
      device.getVendorId(),
      device.getProductId(),
      version
    );
  }

  private void loadDeviceInfo() {
    UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
    assert usbManager != null;
    HashMap<String, UsbDevice> result = usbManager.getDeviceList();
    UsbDevice usbDevice = result.get(this.deviceName);
    if (null == usbDevice) {
      this.finish();
      return;
    }
    this.usbDevice = usbDevice;
    this.deviceInfo = getUsbDeviceInfo(usbDevice);
  }
}
