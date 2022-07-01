package com.example.androidffmpeg.ui.Fragment;

import android.app.Fragment;
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
    private String agrs1=null;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            agrs1 = bundle.getString("agrs1");
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
