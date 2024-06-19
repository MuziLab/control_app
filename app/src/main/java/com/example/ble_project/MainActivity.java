package com.example.ble_project;

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







public class MainActivity extends AppCompatActivity {  //mainactivity字面意思,主活动,app打开时进行的活动,继承自
    //类AppCompatActivity,活动都用这个大类

    String mClientIp = "192.168.45.227";
    int mClientPort = 1;

     private Socket mClientSocket;//套接字定义
    private BufferedReader mClientIn;//读信号,在本程序中没用
    private PrintWriter mClientOut;//写信号
    private int led_size = 16;
    private String pre_position = "test";


    //上面全是变量定义
    //下面的@override,字面意思,改写,开始改写活动大类

    @Override//开始改写AppCompatActivity方法,这是改写标志,之后所有的出现这个标志都是改写父类方法
    protected void onCreate(Bundle savedInstanceState) { //活动大类中的oncreate方法,活动创建时运行,所以下面所有的在活动创建时运行了
        //注意上面中,和下面的super涉及到java的一些语法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置界面



        // 获取屏幕宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        //绑定控件
        Button squareButton = findViewById(R.id.squareButton);
        TextView touch_positon = findViewById(R.id.position);
        RadioGroup mode_change = findViewById(R.id.mode_change);
        SeekBar light = findViewById(R.id.light);
        Button change_layout = findViewById(R.id.change_layout);
        // 设置按钮的宽度和高度为屏幕宽度
        ViewGroup.LayoutParams params = squareButton.getLayoutParams();
        params.width = screenWidth;
        params.height = screenWidth;
        squareButton.setLayoutParams(params);


        class ServerSocketThread extends Thread//创建一个新方法,继承自thread
        {
            @Override
            public void run(){
                System.out.println("连接中*********************************************");
                try{
                    while (true){
                        System.out.println("连接中%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        mClientSocket = new Socket(mClientIp,mClientPort);
                        if (mClientSocket.isConnected()){
                            System.out.println(("连接成功"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    touch_positon.setText("连接成功");
                                }
                            });
                            break;}
                    }
                    mClientIn = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                    mClientOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream())), true);
                    mClientOut.println("hello");//发信号

                }catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        }//注意,上面只是创建了一个新的变量类型,类似与c里面的typedefine,用的话还得创建新变量,如下
        ServerSocketThread new_server = new ServerSocketThread();
        new_server.start();//今天到这,睡觉



        mode_change.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton choose_one = (RadioButton) findViewById(i);
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
                        touch_positon.setText(position);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 在后台线程中执行耗时操作，例如网络请求
                                mClientOut.println(position);

                            }
                        }).start();


                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (position != pre_position)
                        // 手指移动
                        {
                            touch_positon.setText(position);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 在后台线程中执行耗时操作，例如网络请求
                                    mClientOut.println(position);

                                }
                            }).start();
                            pre_position = position;//这个主要是少发送几次,节省资源.
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        // 手指抬起
                        touch_positon.setText("手指抬起");
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



        //切换layout的程序
        change_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }


        });

    }


}




