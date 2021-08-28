package com.example.mythread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DemoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MESSAGE_COUNT_DOWN = 1001;
    public static final int MESSAGE_DONE = 1002;
    private TextView tvTimer;
    private Button btnCount;
    private Handler mhandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initView();
        initHandler();

    }

    private void initHandler() {
        mhandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MESSAGE_COUNT_DOWN:
                        tvTimer.setText(String.valueOf(msg.arg1));
                        break;
                    case MESSAGE_DONE:
                        Toast.makeText(DemoActivity.this, "Tập tiếp đê", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initView() {
        tvTimer = findViewById(R.id.tv_timer);
        btnCount = findViewById(R.id.btn_count);
        btnCount.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_count:
                doCountDown();
                break;
            default:
                break;
        }
    }

    private void doCountDown() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 60;
                do {
                    time--;

                    Message msg = new Message();
                    msg.what = MESSAGE_COUNT_DOWN;
                    msg.arg1 = time;
                    mhandler.sendMessage(msg);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (time > 0);

                mhandler.sendEmptyMessage(MESSAGE_DONE);

            }
        });

        thread.start();

    }
}
