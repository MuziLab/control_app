package com.example.ble_project;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    Fragment2 fragment2 = new Fragment2();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);

        //定义变量
        int led_size = 16;
        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//定义了fragment_transaction


                // 获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        //绑定控件
        Button squareButton = view.findViewById(R.id.squareButton);
        RadioGroup mode_change = view.findViewById(R.id.mode_change);
        SeekBar light = view.findViewById(R.id.light);
        Button more_mode = view.findViewById(R.id.more_mode);

        // 设置按钮的宽度和高度为屏幕宽度
        ViewGroup.LayoutParams params = squareButton.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth;
        squareButton.setLayoutParams(params);

        more_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.FragmentContainer1, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

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
                        break;
                    case "模式二":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                mClientOut.println("M"+(char)(90+2));

                            }
                        }).start();
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
                String position = "X"+x+"Y"+y+"E";
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
                        //if (position != pre_position)
                        // 手指移动
                      //  {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在后台线程中执行耗时操作，例如网络请求
                                    mClientOut.println(position);

                                }
                            }).start();
                            //pre_position = position;//这个主要是少发送几次,节省资源.
                      //  }
                        break;
                    case MotionEvent.ACTION_UP:
                        // 手指抬起
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 在后台线程中执行耗时操作，例如网络请求
                                mClientOut.println("X0Y0E");

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