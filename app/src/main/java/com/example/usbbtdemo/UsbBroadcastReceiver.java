package com.example.usbbtdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.example.usbbtdemo.model.DevicePermission;

public class UsbBroadcastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    if (DevicePermission.Usb.equals(action)) {
      synchronized (this) {
        UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
        boolean permissionGranted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
        if (permissionGranted) {
          DeviceEvents.usbDeviceReadyForCommunicate.emit(usbDevice);
        } else {
            DeviceEvents.usbDevicePermissionDenied.emit(usbDevice);
        }
      }
    }
  }
}
