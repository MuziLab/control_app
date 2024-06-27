package com.example.ble_project;

import android.os.Bundle;
import android.os.PowerManager;
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
import android.content.Context;




public class MainActivity extends AppCompatActivity {  //mainactivity字面意思,主活动,app打开时进行的活动,继承自
    //类AppCompatActivity,活动都用这个大类

    String mClientIp = "192.168.157.108";
    int mClientPort = 1;


     private Socket mClientSocket;//套接字定义
    private BufferedReader mClientIn;//读信号,在本程序中没用
    private PrintWriter mClientOut;//写信号
    private FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();//定义了fragment_transaction
    FirstFragment fragment1 = new FirstFragment();
    private PowerManager.WakeLock wakeLock;
    Fragment2 fragment2 = new Fragment2();

    public PrintWriter getClientOut(){
        return mClientOut;
    }//定义了一个方法,用于向fragment中传数据

    public void replaceToFragment2(){
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.FragmentContainer1, fragment2);
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.commit();
    }//同样用于fragment中


    //上面全是变量定义
    //下面的@override,字面意思,改写,开始改写活动大类

    @Override//开始改写AppCompatActivity方法,这是改写标志,之后所有的出现这个标志都是改写父类方法,注意,之后所以的方法都得是父类有的
    protected void onCreate(Bundle savedInstanceState) { //活动大类中的oncreate方法,活动创建时运行,所以下面所有的在活动创建时运行了
        //注意上面中,和下面的super涉及到java的一些语法
        super.onCreate(savedInstanceState);

        LogFragment logFragment = new LogFragment();
        FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.FragmentContainer1, logFragment);

        fragmentTransaction3.commit();
        // 获取PowerManager
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        // 初始化WakeLock
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::SocketWakeLock");
        // 启用WakeLock
        wakeLock.acquire();

        class ServerSocketThread extends Thread//创建一个新方法,继承自thread
        {
            @Override
            public void run(){
                while (true) {
                    try {
                        System.out.println("连接中%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                        mClientSocket = new Socket(mClientIp, mClientPort);

                        if (mClientSocket.isConnected()) {
                            System.out.println(("连接成功"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fragmentTransaction.replace(R.id.FragmentContainer1, fragment1).commit();//加fragment
                                }
                            });
                            mClientIn = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                            mClientOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientSocket.getOutputStream())), true);
                            mClientOut.println("hello");//发信号
                            break;
                        }


                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }

            }
        }//注意,上面只是创建了一个新的变量类型,类似与c里面的typedefine,用的话还得创建新变量,如下
        ServerSocketThread new_server = new ServerSocketThread();
        new_server.start();//今天到这,睡觉
        setContentView(R.layout.activity_main);//设置界面


    }


}




