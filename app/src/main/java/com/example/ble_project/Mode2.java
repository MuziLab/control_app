package com.example.ble_project;


import android.os.Bundle;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.PrintWriter;

public class Mode2 extends Fragment {
    Mode2_1 mode2_1 = new Mode2_1();
    Mode2_2 mode2_2 = new Mode2_2();
    Switch switch1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mode_2, container, false);
        switch1 = view.findViewById(R.id.switch1);
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainer2_2,mode2_1);
        fragmentTransaction.commit();
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 处理开关变化
                if (isChecked) {
                    // Switch打开
                    switch1.setText("累加模式");
                    FragmentTransaction fragmentTransaction1 = getChildFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.FragmentContainer2_2,mode2_2);
                    fragmentTransaction1.commit();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mClientOut.println("M"+(char)(90+4));
                        }
                    }).start();
                } else {
                    // Switch关闭
                    switch1.setText("尾迹模式");
                    FragmentTransaction fragmentTransaction2 = getChildFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.FragmentContainer2_2,mode2_1);
                    fragmentTransaction2.commit();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mClientOut.println("M"+(char)(90+2));
                        }
                    }).start();
                }
            }
        });

        return view;
    }

    public boolean switchCheck(){
        return switch1.isChecked();
    }
}
