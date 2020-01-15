package com.example.usbbtdemo.model;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.usbbtdemo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class DeviceViewHolder extends RecyclerView.ViewHolder {

  public interface OnDeviceClickListener {
    void onClick(int position);
  }

  private TextView typeLabel;
  private TextView nameLabel;

  DeviceViewHolder(@NonNull View itemView, final OnDeviceClickListener onClickListener) {
    super(itemView);
    typeLabel = itemView.findViewById(R.id.device_type_label);
    nameLabel = itemView.findViewById(R.id.device_name_label);
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position = getLayoutPosition();
        if (null != onClickListener) {
          onClickListener.onClick(position);
        }
      }
    });
  }

  void bind(Device device) {
    boolean isUsb = device.getType().equals(Device.USB);
    typeLabel.setText(isUsb ? "U" : "B");
    nameLabel.setText(device.getName());
    if (isUsb) {
      typeLabel.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimaryDark));
    } else {
      typeLabel.setTextColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
    }
  }
}
