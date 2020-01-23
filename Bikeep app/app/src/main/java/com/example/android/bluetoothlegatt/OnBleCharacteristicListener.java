package com.example.android.bluetoothlegatt;

/**
 * Created by nirga on 17/12/15.
 */
public interface OnBleCharacteristicListener {
    void onCharacteristicRead(byte[] data);
}
