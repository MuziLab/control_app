package com.example.ble_project;


import android.os.Bundle;
import android.widget.CompoundButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Mode2 extends Fragment {
    Mode2_1 mode2_1 = new Mode2_1();
    Mode2_2 mode2_2 = new Mode2_2();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mode_2, container, false);
        Switch switch1 = view.findViewById(R.id.switch1);
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
                } else {
                    // Switch关闭
                    switch1.setText("尾迹模式");
                    FragmentTransaction fragmentTransaction2 = getChildFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.FragmentContainer2_2,mode2_1);
                    fragmentTransaction2.commit();
                }
            }
        });

        return view;
    }
}
