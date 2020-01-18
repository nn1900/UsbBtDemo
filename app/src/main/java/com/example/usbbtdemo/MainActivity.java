package com.example.usbbtdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.usbbtdemo.model.Device;
import com.example.usbbtdemo.model.DeviceListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private DeviceListAdapter adapter;
  private List<Device> deviceList = new ArrayList<>();
  private Map<String, Device> deviceMap = new HashMap<>();
  private TextView emptyView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initUI();

    initDevices();

    updateUI();
  }

  private void initUI() {
    emptyView = findViewById(R.id.empty_view);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setTitle(R.string.main_activity_title);

    recyclerView = findViewById(R.id.device_list);

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);

    adapter = new DeviceListAdapter(deviceList);
    recyclerView.setAdapter(adapter);
  }

  private void initDevices() {
    Intent intent = this.getIntent();
    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
    if (device != null) {
      Device deviceInfo = getUsbDeviceInfo(device);
      adapter.addDevice(deviceInfo);
      deviceMap.put(device.getDeviceName(), deviceInfo);
    }

    // enumerate devices
    UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
    assert usbManager != null;
    HashMap<String, UsbDevice> result = usbManager.getDeviceList();
    for (String key: result.keySet()) {
      UsbDevice usbDevice = result.get(key);
      assert usbDevice != null;
      Device deviceInfo = getUsbDeviceInfo(usbDevice);
      if (!deviceMap.containsKey(deviceInfo.getName())) {
        adapter.addDevice(deviceInfo);
      }
    }
  }

  private void updateUI() {
    if (deviceList.size() > 0) {
      emptyView.setVisibility(View.GONE);
      recyclerView.setVisibility(View.VISIBLE);
    } else {
      emptyView.setVisibility(View.VISIBLE);
      recyclerView.setVisibility(View.INVISIBLE);
    }
  }

  private Device getUsbDeviceInfo(UsbDevice device) {
    String version = "";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) version = device.getVersion();
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
}
