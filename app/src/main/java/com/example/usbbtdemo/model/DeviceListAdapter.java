package com.example.usbbtdemo.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.usbbtdemo.R;
import com.example.usbbtdemo.UsbDeviceActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {
  private List<Device> devices;

  public DeviceListAdapter(List<Device> devices) {
    this.devices = devices;
    this.setHasStableIds(true);
  }

  @NonNull
  @Override
  public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    ViewGroup container = (ViewGroup)layoutInflater.inflate(R.layout.device_list_item, parent, false);
    return new DeviceViewHolder(container, new DeviceViewHolder.OnDeviceClickListener() {
      @Override
      public void onClick(int position) {
        Device deviceInfo = devices.get(position);
        Intent intent = new Intent(context, UsbDeviceActivity.class);
        intent.putExtra("deviceName", deviceInfo.getName());
        context.startActivity(intent);
      }
    });
  }

  @Override
  public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
    holder.bind(devices.get(position));
  }

  @Override
  public int getItemCount() {
    return devices != null ? devices.size() : 0;
  }

  public void addDevice(Device device) {
    this.devices.add(device);
    this.notifyDataSetChanged();
  }

  @Override
  public long getItemId(int position) {
    return devices.get(position).getId();
  }
}
