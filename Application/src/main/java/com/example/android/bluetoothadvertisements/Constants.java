/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.bluetoothadvertisements;

import android.os.ParcelUuid;

/**
 * Constants for use in the Bluetooth Advertisements sample
 */
public class Constants {

    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * UUID identified with this app - set as Service UUID for BLE Advertisements.
     * <p>
     * Bluetooth requires a certain format for UUIDs associated with Services.
     * The official specification can be found here:
     * {@link https://www.bluetooth.org/en-us/specification/assigned-numbers/service-discovery}
     */
    public static String deviceMAC = "F8:F0:05:F4:CC:20";//"F8:F0:05:F3:53:BF"
    public static String inputId = "AAAA";

    public static int manufacturerId() {
        return Integer.parseInt(inputId, 16);
    }//max FF FF = 65535, bytes reversed

    public static String inputData = "01234567890abcde01234567890abcde01234567890abcde";

    public static byte[] manufactureData() {
        return hexStringToByteArray(inputData);
    }

//    public static final ParcelUuid Service_UUID = ParcelUuid.fromString("56c4ad3e-fb42-4d19-b01c-cd01d831b25a");
//    public static final ParcelUuid serviceUUID =ParcelUuid.fromString("56c4ad3e-fb42-4d19-b01c-cd01d831b25a");
//    public static final byte[] serviceData = hexStringToByteArray("00000014-1234-5678-9012-123456789012");

    public static final int REQUEST_ENABLE_BT = 1;

}