package com.example.jessie.responsetest;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ExecuteActivity extends AppCompatActivity implements View.OnTouchListener {
    Handler handler;
    int a=0;
    int p=0;
    TextView tv;
    Button [] btn;
    int [] b={R.id.button8,R.id.button9,R.id.button10};
    String [] result;
    String [] time;
    String [] speed;
    String r="";
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute);
        tv=(TextView)findViewById(R.id.textView);
        result = new String[27];
        time = new String[27];
        speed = new String[27];
        for(int k=0;k<result.length;k++){
            result[k]="";
            time[k]="";
            speed[k]="";
        }
        btn=new Button[3];
        for(int i=0;i<btn.length;i++){
            btn[i]=(Button)findViewById(b[i]);
            result[i]="";
            btn[i].setOnTouchListener(this);
            btn[i].setEnabled(false);
        }

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        if(((p%150) == 0) && a<27 ){//((p%1500) == 0) && a<27

                                btn[0].setEnabled(true);
                                btn[1].setEnabled(true);
                                btn[2].setEnabled(true);

                            a++;
                        }
                        if (p>(450)){//1500*27+500
                            Intent intent = new Intent(ExecuteActivity.this, EndActivity.class);
                            Bundle bundle=new Bundle();
                            //bundle.putString("record",r);
                            //bundle.putStringArray("record",result);
                            bundle.putStringArray("time",time);
                            bundle.putStringArray("speed",speed);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }else {
                            p+=1;
                            tv.setText("第"+a+"個 "+"第:"+String.format("%.2f",p/100.0)+"秒");
                            handler.sendEmptyMessageDelayed(0,10);//每50毫秒再做一次編號0 process
                        }
                        break;
                }
            }
        };
        handler.sendEmptyMessage(0);//做編號0這個process
    }

    @SuppressLint("MissingPermission")
    public void btn_onClick(View view){
        if(view == btn[0]){
            // r+=tv.getText().toString()+" 減速 ";
            //result[a-1]=""+a+","+String.format("%.2f",(p%1500)/100.0)+","+" 減速";//"第"+a+"個 "+"花:"+String.format("%.2f",(p%1500)/100.0)+"秒"+" 減速"
            time[a-1] = String.format("%.2f",(p%1500)/100.0);
            speed[a-1] =  "減速";
            btn[0].setEnabled(false);
            btn[1].setEnabled(false);
            btn[2].setEnabled(false);
            Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0,200},-1);
        }else if (view == btn[1]){
            //r+=tv.getText().toString()+" 等速 ";
            //result[a-1]=""+a+","+String.format("%.2f",(p%1500)/100.0)+","+" 等速 ";
            time[a-1] = String.format("%.2f",(p%1500)/100.0);
            speed[a-1] =  "等速";
            btn[0].setEnabled(false);
            btn[1].setEnabled(false);
            btn[2].setEnabled(false);
            try{
                Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(new long[]{0,200},-1);
            }catch (NullPointerException e){

            }

        }else if (view == btn[2]){
            //r+=tv.getText().toString()+" 加速 ";
            //result[a-1]=""+a+","+String.format("%.2f",(p%1500)/100.0)+","+" 加速" ;
            time[a-1] = String.format("%.2f",(p%1500)/100.0);
            speed[a-1] =  "加速";
            btn[0].setEnabled(false);
            btn[1].setEnabled(false);
            btn[2].setEnabled(false);
            Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0,200},-1);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Vibrator vb=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            vb.vibrate(200);
        }
        return false;
    }
}
