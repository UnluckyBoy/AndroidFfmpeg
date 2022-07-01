package com.example.androidffmpeg.ui.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;

import com.example.androidffmpeg.R;
import com.example.androidffmpeg.Tools.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EditFragment extends Fragment {
    private View view;
    private String agrs1=null;
    private TextView mVideo_Text,mAudio_Text;
    private Button mVideo_Button,mAudio_Button;

    String path = "NULL";

    public static EditFragment newInstance(String param1) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
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
        if(view!=null){
            ViewGroup parent=(ViewGroup) view.getParent();
            if(parent!=null){
                parent.removeView(view);
            }
        }else{
            view = inflater.inflate(R.layout.editfragment, container, false);

            Bundle bundle = getArguments();
            agrs1 = bundle.getString("agrs1");
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

        mVideo_Text = view.findViewById(R.id.mVideo_text);
        mAudio_Text = view.findViewById(R.id.mAudio_text);
        mVideo_Button=view.findViewById(R.id.video_button);
        mAudio_Button=view.findViewById(R.id.audio_button);
        //intoFileManager();
        GetVideoFile();
    }

    /**
     * 按钮函数
     */
    private void GetVideoFile(){
        mVideo_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntoFileManager();
            }
        });
    }

    private void IntoFileManager() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        /*intent.setType(“image/*”);选择图片,intent.setType(“audio/*”);//选择音频,
        intent.setType(“video/*”);//选择视频 （mp4 3gp 是android支持的视频格式）
        intent.setType(“video/*;image/*”);//同时选择视频和图片*/
        intent.setType("*/*");//无类型限制:
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
        /*
        //打开Android/data文件夹目录
        try {
            Uri uri = Uri.parse("content://com.android.externalstorage.documents/document/primary%3AAndroid%2Fdata");
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
            //flag看实际业务需要可再补充
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            getActivity().startActivityForResult(intent, 6666);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        switch (requestCode) {
            case 6666:
                if (resultCode == getActivity().RESULT_OK) {
                    //persist uri
                    getActivity().getContentResolver().takePersistableUriPermission(data.getData(),
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                    //now use DocumentFile to do some file op
                    DocumentFile documentFile = DocumentFile
                            .fromTreeUri(getActivity(), data.getData());
                    DocumentFile[] files = documentFile.listFiles();
                }
                break;
            default:
                break;
        }
        */

        String path;
        if (resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开
                path = uri.getPath();
                mVideo_Text.setText(path);
                Log.i("queryfilepath", "返回结果1: " + path);
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = FileUtil.getPath(getActivity(), uri);
                mVideo_Text.setText(path);
                Log.i("queryfilepath", "返回结果2: " + path);

            } else {//4.4以下下系统调用方法
                path = FileUtil.getRealPathFromURI(uri);
                mVideo_Text.setText(path);
                Log.i("queryfilepath", "返回结果3: " + path);
            }
            try {
                //保存读取到的内容
                StringBuilder result = new StringBuilder();
                //获取输入流
                InputStream is = getActivity().getContentResolver().openInputStream(uri);
                //创建用于字符输入流中读取文本的bufferReader对象
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    //将读取到的内容放入结果字符串
                    result.append(line);
                    Log.i("______读取的内容：","-----:"+result);
                }
                //文件中的内容
                String content = result.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
