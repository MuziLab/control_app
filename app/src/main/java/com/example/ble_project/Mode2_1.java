package com.example.ble_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.PrintWriter;

public class Mode2_1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mode_2_1, container, false);
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();
        Button clear_button = view.findViewById(R.id.clear_button);
        SeekBar length_of_tail = view.findViewById(R.id.seekBar5);
        TextView text = view.findViewById(R.id.textView5);
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("C");
                    }
                }).start();
            }
        });

        length_of_tail.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                text.setText("尾巴长度:"+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("D"+(char)(seekBar.getProgress()+90));

                    }
                }).start();
            }
        });

        return view;
    }
}