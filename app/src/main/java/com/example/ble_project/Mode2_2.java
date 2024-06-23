package com.example.ble_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

import java.io.PrintWriter;

public class Mode2_2 extends Fragment {
    private int r_value = 0;
    private int g_value = 0;
    private int b_value = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mode_2_2, container, false);
        SeekBar r_seek = view.findViewById(R.id.seekBar6);
        SeekBar g_seek = view.findViewById(R.id.seekBar7);
        SeekBar b_seek = view.findViewById(R.id.seekBar8);
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();
        r_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                r_value = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("G"+(char)(r_value+90)+(char)(g_value+90)+(char)(b_value+90));
                    }
                }).start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        g_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                g_value = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("G"+(char)(r_value+90)+(char)(g_value+90)+(char)(b_value+90));
                    }
                }).start();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                b_value = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("G"+(char)(r_value+90)+(char)(g_value+90)+(char)(b_value+90));
                    }
                }).start();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }
}