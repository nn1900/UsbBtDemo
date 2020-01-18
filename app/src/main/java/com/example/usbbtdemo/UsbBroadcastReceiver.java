package com.example.usbbtdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.example.usbbtdemo.model.DevicePermission;

public class UsbBroadcastReceiver extends BroadcastReceiver {
  private static final String TAG = "usb_broadcast_receiver";

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();

    if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
      UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
      DeviceEvents.usbDeviceAttached.emit(usbDevice);
      return;
    }

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
      return;
    }

    if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
      // device detached.
      Log.i(TAG, "USB detached");
      UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
      DeviceEvents.usbDeviceDetached.emit(usbDevice);
    }
  }
}
