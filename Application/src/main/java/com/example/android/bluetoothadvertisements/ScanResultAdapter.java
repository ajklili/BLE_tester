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

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Holds and displays {@link ScanResult}s, used by {@link ScannerFragment}.
 */
public class ScanResultAdapter extends BaseAdapter {

    private ArrayList<ScanResult> mArrayList;

    private Context mContext;

    private LayoutInflater mInflater;

    ScanResultAdapter(Context context, LayoutInflater inflater) {
        super();
        mContext = context;
        mInflater = inflater;
        mArrayList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mArrayList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        // Reuse an old view if we can, otherwise create a new one.
        if (view == null) {
            view = mInflater.inflate(R.layout.listitem_scanresult, null);
        }

        TextView deviceTXpowerView = (TextView) view.findViewById(R.id.device_TXpower);
        TextView deviceDataView = (TextView) view.findViewById(R.id.device_data);
        TextView lastSeenView = (TextView) view.findViewById(R.id.last_seen);

        ScanResult scanResult = mArrayList.get(position);
        long timeStamp = scanResult.getTimestampNanos();
        long timeStampPrev = 0;
        if (position > 0) {
            timeStampPrev = mArrayList.get(position - 1).getTimestampNanos();
        }

        String MAC = scanResult.getDevice().getAddress();

        deviceTXpowerView.setText(String.valueOf(scanResult.getRssi()));

        deviceDataView.setText(getRawDataString(scanResult));
        lastSeenView.setText(getTimeSinceString(mContext, timeStamp, timeStampPrev));

        return view;
    }

//    /**
//     * Search the adapter for an existing device address and return it, otherwise return -1.
//     */
//    private int getPosition(String address) {
//        int position = -1;
//        for (int i = 0; i < mArrayList.size(); i++) {
//            if (getRawDataString(mArrayList.get(i)).equals(address)) {
//                position = i;
//                break;
//            }
//        }
//        return position;
//    }


    /**
     * Add a ScanResult item to the adapter if a result from that device isn't already present.
     * Otherwise updates the existing position with the new ScanResult.
     */
    public void add(ScanResult scanResult) {

//        int existingPosition = getPosition(getRawDataString(scanResult));
//        if (existingPosition >= 0) {
//            // Device is already in list, update its record.
//            mArrayList.set(existingPosition, scanResult);
//        } else {
//            // Add new Device's ScanResult to list.
//            mArrayList.add(scanResult);
//        }
        mArrayList.add(scanResult);
    }

    /**
     * Clear out the adapter.
     */
    public void clear() {
        mArrayList.clear();
    }

    /**
     * Takes in a number of nanoseconds and returns a human-readable string giving a vague
     * description of how long ago that was.
     */
    public static String getTimeSinceString(Context context, long timeNanoseconds, long timeNanosecondsPrev) {
        String prefixText = "";

//        long timeSince = SystemClock.elapsedRealtimeNanos() - timeNanoseconds;
        long timeInterval = timeNanoseconds - timeNanosecondsPrev;
        if (timeNanosecondsPrev == 0)
            timeInterval = SystemClock.elapsedRealtimeNanos() - timeNanoseconds;
        long milliSecondsSince = TimeUnit.MILLISECONDS.convert(timeInterval, TimeUnit.NANOSECONDS);

        return (prefixText + milliSecondsSince + " ms");
//        return ""+TimeUnit.SECONDS.convert(SystemClock.elapsedRealtimeNanos(), TimeUnit.NANOSECONDS);
    }

    public static String getRawDataString(ScanResult scanResult) {
        byte[] rawData = scanResult.getScanRecord().getBytes();
        String rawDataString = "";
        for (byte b : rawData) {
            rawDataString += String.format("%02x", b);
        }
        String payload=rawDataString.substring(0, 61)+", "+rawDataString.substring(61, 62);
        return payload;
    }
}
