package com.example.ble_project;

import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;


import android.util.DisplayMetrics;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;




public class FirstFragment extends Fragment {

    private String pre_position = "01";
    private String position;

    Mode2 mode2 = new Mode2();
    Mode1 mode1 = new Mode1();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentContainer2,mode1);
        fragmentTransaction.commit();
        //定义变量
        int led_size = 16;
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();



                // 获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        //绑定控件
        Button squareButton = view.findViewById(R.id.squareButton);
        RadioGroup mode_change = view.findViewById(R.id.mode_change);
        SeekBar light = view.findViewById(R.id.light);
        TextView text_xy = view.findViewById(R.id.textViewXy);

        // 设置按钮的宽度和高度为屏幕宽度
        ViewGroup.LayoutParams params = squareButton.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth;
        squareButton.setLayoutParams(params);





        mode_change.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton choose_one = (RadioButton) view.findViewById(i);
                String choose_text = (String) choose_one.getText();
                switch (choose_text){
                    case "模式一":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClientOut.println("M"+(char)(90+1));
                            }
                        }).start();
                        FragmentTransaction fragmentTransaction1 = getChildFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.FragmentContainer2,mode1);
                        fragmentTransaction1.commit();
                        break;
                    case "模式二":
                        FragmentTransaction fragmentTransaction2 = getChildFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.FragmentContainer2,mode2);
                        fragmentTransaction2.commit();

                        boolean switch_state = false;//这里还要优化,
                        if (switch_state == true){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("模式4");
                                mClientOut.println("M"+(char)(90+4));


                            }
                        }).start();}else {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {


                                    mClientOut.println("M"+(char)(90+2));


                                }
                            }).start();
                        }

                        break;
                    case "模式三":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClientOut.println("M"+(char)(90+3));
                            }
                        }).start();
                        break;
                    default:
                        System.out.println("error");
                        break;
                }
            }
        });

        light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("L"+(char)(90+i));
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



        squareButton.setOnTouchListener(new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取手指位置
                int x = (int)((led_size*event.getX())/screenWidth)+1;
                int y = (int)((led_size*event.getY())/screenWidth)+1;//根据点阵长度设置
                if (y<1){y=1;}
                if(y>16){y=16;}
                position = "F"+(char)(x+90)+(char)(y+90)+"E";
                if (position.equals(pre_position))
                {

                }else
                {

                    pre_position = position;
                    text_xy.setText("X:"+x+"Y:"+y);

                    // 根据不同的触摸事件做处理
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // 手指按下

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在后台线程中执行耗时操作，例如网络请求

                                    mClientOut.println(position);



                                }
                            }).start();


                            break;
                        case MotionEvent.ACTION_MOVE:
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在后台线程中执行耗时操作，例如网络请求

                                    mClientOut.println(position);

                                }
                            }).start();


                            break;
                    }

                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        // 手指抬起
                        text_xy.setText("手指抬起");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClientOut.println("I" );

                            }
                        }).start();
                        break;
                }
                return true;
            }
        });
















        return view;
    }
}