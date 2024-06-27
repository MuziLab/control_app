package com.example.ble_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button back_button = view.findViewById(R.id.back_button);
        Button clear_button = view.findViewById(R.id.clear_button);
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("H");
                    }
                }).start();
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_seek.setProgress(0);
                g_seek.setProgress(0);
                b_seek.setProgress(15);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("C");
                    }
                }).start();
            }
        });

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