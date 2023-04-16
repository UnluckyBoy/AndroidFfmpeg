package com.example.androidffmpeg.ui.Fragment;

import androidx.fragment.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.androidffmpeg.R;

public class MainFragment extends Fragment {
    //private ActivityMainBinding binding;
    private View view;
    private String args=null;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("args", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.mainfragment, container, false);

            Bundle bundle = getArguments();
            args = bundle.getString("args");
            //执行Fragment视图数据初始化
            InitFragmentData(view);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void InitFragmentData(final View view){

        TextView tv = view.findViewById(R.id.main_fragment_text);
        tv.setText(stringFromJNI());
        Log.i("",stringFromJNI());
    }

    public native String stringFromJNI();
}
