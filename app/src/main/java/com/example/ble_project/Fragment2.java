
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

public class Fragment2 extends Fragment {

    private int mic_s = 1;
    private int sleep_ti = 1;
    private int sleep_th = 5;
    private int gravity_co = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        Button apply_but_not_save =  view.findViewById(R.id.button2);
        Button apply_and_save =  view.findViewById(R.id.button);
        SeekBar mic_sensitive = view.findViewById(R.id.seekBar3);
        SeekBar sleep_time = view.findViewById(R.id.seekBar2);
        SeekBar sleep_threshold = view.findViewById(R.id.seekBar);
        SeekBar gravity_coefficient = view.findViewById(R.id.seekBar4);
        TextView Mic = view.findViewById(R.id.textView10);
        TextView SleepTi = view.findViewById(R.id.textView13);
        TextView SleepTh = view.findViewById(R.id.textView12);
        TextView Gravity = view.findViewById(R.id.textView15);

        PrintWriter mClientOut;//写信号
        mClientOut = ((MainActivity) getActivity()).getClientOut();

        mic_sensitive.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Mic.setText("mic灵敏度:"+i);
                mic_s = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sleep_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SleepTi.setText("瞌睡时间:"+i+"秒");
                sleep_ti = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sleep_threshold.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                SleepTh.setText("瞌睡阈值:"+i);
                sleep_th = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        gravity_coefficient.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Gravity.setText("重力系数:"+i);
                gravity_co = i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        apply_and_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("A"+(char)(mic_s+90)+(char)(sleep_ti+90)+(char)(sleep_th+90)+(char)(gravity_co+90));
                    }
                }).start();

            }
        });
        apply_but_not_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mClientOut.println("B"+(char)(mic_s+90)+(char)(sleep_ti+90)+(char)(sleep_th+90)+(char)(gravity_co+90));
                    }
                }).start();

            }
        });


        return view;
    }
}