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

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;







public class MainActivity extends AppCompatActivity {  //mainactivity字面意思,主活动,app打开时进行的活动,继承自
    //类AppCompatActivity,活动都用这个大类

    String mClientIp = "192.168.45.227";
    int mClientPort = 1;
    /** 返回两个整型变量数据的较大值 */


     private Socket mClientSocket;//套接字定义
    private BufferedReader mClientIn;//读信号,在本程序中没用
    private PrintWriter mClientOut;//写信号

    public PrintWriter getClientOut(){
        return mClientOut;
    }


    //上面全是变量定义
    //下面的@override,字面意思,改写,开始改写活动大类

    @Override//开始改写AppCompatActivity方法,这是改写标志,之后所有的出现这个标志都是改写父类方法,注意,之后所以的方法都得是父类有的
    protected void onCreate(Bundle savedInstanceState) { //活动大类中的oncreate方法,活动创建时运行,所以下面所有的在活动创建时运行了
        //注意上面中,和下面的super涉及到java的一些语法
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置界面


        TextView touch_position = findViewById(R.id.position);
        Button change_layout = findViewById(R.id.button);


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
                                    touch_position.setText("连接成功");
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



          FirstFragment fragment1 = new FirstFragment();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.add(R.id.fragment1,new Example);


        //切换layout的程序
        change_layout.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer1, fragment1).commit();
            }
        });

    }


}




