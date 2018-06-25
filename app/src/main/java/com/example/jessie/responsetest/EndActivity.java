package com.example.jessie.responsetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class EndActivity extends AppCompatActivity {
    TextView []tv;
    int []t={R.id.textView15,R.id.textView14,R.id.textView13,R.id.textView12,R.id.textView11,R.id.textView10,R.id.textView9,
            R.id.textView8,R.id.textView7,R.id.textView24,R.id.textView23,R.id.textView22,R.id.textView21,R.id.textView20,R.id.textView19,
            R.id.textView18,R.id.textView17,R.id.textView16,R.id.textView33,R.id.textView32,R.id.textView31,R.id.textView30,R.id.textView29,
            R.id.textView28,R.id.textView27,R.id.textView26,R.id.textView25,};
    String []endT;
    String []endS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        getRecord();
        //setUserData();
    }


    private void getRecord(){
        tv=new TextView[27];
        Bundle bundle=this.getIntent().getExtras();
        //endT=bundle.getStringArray("record");
        endT=bundle.getStringArray("time");
        endS=bundle.getStringArray("speed");
        for(int j=0;j<tv.length;j++){
            tv[j]=(TextView)findViewById(t[j]);
            tv[j].setText(""+(j+1)+","+endT[j]+","+endS[j]);
        }

    }
    public void btn_onClick3(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;

        //pushFriend("betty");
        setUserData();

        try{
            fos = openFileOutput("record.txt", Activity.MODE_APPEND);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            for (int c=0;c<endT.length;c++) {
                bw.write(""+(c+1)+","+endT[c]+","+endS[c]+"\n");
                //bw.write(endT[c]+"\n");
            }
            bw.write("\n");
            Toast.makeText(this,"紀錄已儲存",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException fn){
            Log.e("Err","FileNotFound");
        }catch (IOException io){
            Log.e("Err","Write Failed");
        }finally {
            try{
                bw.close();
            }catch (IOException io2){
                Log.e("Err","close failed");
            }
        }

        finish();
    }
    private void setUserData() {
        // write user data by setValue()
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("Light");
        DatabaseReference RecsRef=
        usersRef.push();
        Map<String, Object> rec = new HashMap<>();
        for(int i =0;i<endT.length;i++) {
            rec.put("time", endT[i]);
            rec.put("speed", endS[i]);
            RecsRef.child(""+(i+1)).setValue(rec);
        }

        //usersRef.child("1").child("phone").setValue("55633221");
        //usersRef.child("1").child("nickname").setValue("Hank");
        //update user data
        //Map<String, Object> data = new HashMap<>();
        //data.put("nickname", "Hank123");
       /* usersRef.child("1").updateChildren(data,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError,
                                           DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            //正確完成
                            Toast.makeText(EndActivity.this, "Successful !!!",Toast.LENGTH_SHORT).show();
                        } else {
                            //發生錯誤
                            Toast.makeText(EndActivity.this,"Failed !!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
    }
    private void pushFriend(String name){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = db.getReference("Light");
        DatabaseReference friendsRef =
                usersRef.child("2").child("friends").push();
        Map<String, Object> friend = new HashMap<>();
        friend.put("name", name);
        friend.put("phone", "22334455");
        friendsRef.setValue(friend);
        String friendId = friendsRef.getKey();
        Log.d("FRIEND", friendId+"");
    }
    private void saveData(View v){

    }
}
