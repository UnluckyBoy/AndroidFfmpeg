package com.example.androidffmpeg.ui.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.androidffmpeg.R;
import com.example.androidffmpeg.databinding.ActivityMainBinding;
import com.example.androidffmpeg.ui.Fragment.ChatFragment;
import com.example.androidffmpeg.ui.Fragment.EditFragment;
import com.example.androidffmpeg.ui.Fragment.MainFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    // Used to load the 'androidffmpeg' library on application startup.
    static {
        System.loadLibrary("androidffmpeg");
    }

    private ActivityMainBinding binding;
    private BottomNavigationBar bottomNavigationBar;
    private int lastSelectedPosition = 0;

    private MainFragment mMainFragment;
    private ChatFragment mChatFragment;
    private EditFragment mEditFragment;

    private String TAG = MainActivity.class.getSimpleName();
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Init();
    }

    private void Init(){
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        InitBar();
        setDefaultFragment();

        // Example of a call to a native method
        //TextView tv = binding.sampleText;
        //tv.setText(stringFromJNI());
        //Log.i("",stringFromJNI());
    }

    public void InitBar(){
        HideMainBar();

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bar_main);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "主页").setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.chat, "论坛").setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.video, "玩乐").setActiveColor(R.color.blue))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void setDefaultFragment() {
        Intent intent=getIntent();
        String id=intent.getStringExtra("U_id");
        //Log.i("MainActivity中的id",id);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(id==null){
            mMainFragment = MainFragment.newInstance("");
        }else{
            mMainFragment = MainFragment.newInstance(id);
        }
        transaction.replace(R.id.tb,mMainFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        Intent intent=getIntent();
        String id=intent.getStringExtra("U_id");

        FragmentManager fm = this.getFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (mMainFragment == null) {
                    mMainFragment = MainFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, mMainFragment);
                //this.finish();
                break;
            case 1:
                if (mChatFragment == null) {
                    mChatFragment = ChatFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, mChatFragment);
                //this.finish();
                break;
            case 2:
                if (mEditFragment == null) {
                    mEditFragment = EditFragment.newInstance(id);
                }
                transaction.replace(R.id.tb, mEditFragment);
                //this.finish();
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }
    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 主界面隐藏标题栏
     */
    private void HideMainBar(){
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.hide(); //隐藏标题栏
        }
    }

    //退出应用提示
    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        }
        else{//退出程序
            this.finish();
            System.exit(0);
        }
    }
    /**
     * A native method that is implemented by the 'androidffmpeg' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();

}