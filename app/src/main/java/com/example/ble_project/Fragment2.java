
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        return view;
    }
}