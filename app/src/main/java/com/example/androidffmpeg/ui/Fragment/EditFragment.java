package com.example.androidffmpeg.ui.Fragment;

import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;
import static com.example.androidffmpeg.Tools.FileUtil.getPath;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidffmpeg.FfmpegCmd;
import com.example.androidffmpeg.R;
import com.example.androidffmpeg.Tools.lister.OnHandleListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EditFragment extends Fragment {
    private static int REQUEST_CODE_FOR_DIR=1;

    private View view;
    private String args = null;
    private TextView mVideo_Text, mAudio_Text;
    private Button mVideo_Btn, mAudio_Btn,mBuild_Btn;
    private String mBtnType=null;
    private String video_name,audio_name,out_name="Out.mp4";

    public static EditFragment newInstance(String param1) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString("args", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public EditFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        } else {
            view = inflater.inflate(R.layout.editfragment, container, false);

            Bundle bundle = getArguments();
            args = bundle.getString("args");

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

    private void InitFragmentData(final View view) {

        mVideo_Text = view.findViewById(R.id.mVideo_text);
        mAudio_Text = view.findViewById(R.id.mAudio_text);
        mVideo_Btn = view.findViewById(R.id.video_button);
        mAudio_Btn = view.findViewById(R.id.audio_button);
        mBuild_Btn= view.findViewById(R.id.build_video);

        mVideo_Btn.setOnClickListener(new BtnClickListener());
        mAudio_Btn.setOnClickListener(new BtnClickListener());
        mBuild_Btn.setOnClickListener(new BtnClickListener());
    }

    private class BtnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.video_button:
                    IntoFileManager();
                    mBtnType="video";
                    break;
                case R.id.audio_button:
                    IntoFileManager();
                    mBtnType="audio";
                    break;
                case R.id.build_video:
                    //Toast.makeText(view.getContext(),"Ffmpeg版本:"+getVersion(),Toast.LENGTH_SHORT).show();
                    String concatAudioCmd = "ffmpeg -i %s -i %s -codec copy %s";
                    String[] temp = String.format(concatAudioCmd,video_name,audio_name,out_name).split(" ");
                    //Toast.makeText(view.getContext(),temp.toString(),Toast.LENGTH_SHORT).show();
//                    FfmpegCmd.execute(temp, new OnHandleListener() {
//                        @Override
//                        public void onBegin() {
//                            Toast.makeText(view.getContext(),"开始",Toast.LENGTH_SHORT).show();
//                        }
//                        @Override
//                        public void onMsg(String msg) {
//
//                        }
//                        @Override
//                        public void onProgress(int progress, int duration) {
//
//                        }
//                        @Override
//                        public void onEnd(int resultCode, String resultMsg) {
//                            Toast.makeText(view.getContext(),"结束",Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    break;
            }
        }
    }

    public native String getVersion();//回调C++接口


    /**
     * 打开文件夹
     **/
    private void IntoFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//筛选器
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.putExtra(EXTRA_ALLOW_MULTIPLE, true);//设置多选
        startActivityForResult(Intent.createChooser(intent,"选择一个文件"),1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            //Uri uri = data.getData();//获取URI
            //Toast.makeText(getActivity(),"获取的文件内容:"+uri.getPath(),Toast.LENGTH_SHORT).show();
            GetFileName(data.getData());
       }
    }

    private void GetFileName(Uri uri){
        DocumentFile documentFile = DocumentFile.fromSingleUri(view.getContext(), uri);
        if (documentFile != null){
            //Toast.makeText(getActivity(),"获取的文件内容:"+documentFile.getName(),Toast.LENGTH_SHORT).show();
            switch (mBtnType){
                case "video":
                    mVideo_Text.setText(documentFile.getName());
                    video_name=documentFile.getName();
                    break;
                case "audio":
                    mAudio_Text.setText(documentFile.getName());
                    audio_name=documentFile.getName();
                    break;
            }
        }
    }
}
