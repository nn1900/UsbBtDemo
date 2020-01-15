package com.example.usbbtdemo;

import java.util.Observable;

public class DeviceEvents {
  public static class ObservableObject<T> extends Observable {
    private Object lock = new Object();

    public void emit() {
      synchronized (lock) {
        setChanged();
        notifyObservers();
      }
    }

    public void emit(T data) {
      synchronized (lock) {
        setChanged();
        notifyObservers(data);
      }
    }
  }

  public static ObservableObject<Object> usbDeviceAttached = new ObservableObject<>();
  public static ObservableObject<Object> usbDeviceReadyForCommunicate= new ObservableObject<>();
  public static ObservableObject<Object> usbDevicePermissionDenied= new ObservableObject<>();
  public static ObservableObject<Object> usbDeviceDetached = new ObservableObject<>();
}
