package com.example.androidffmpeg.ui.Fragment;

import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;
import static com.example.androidffmpeg.Tools.FileUtil.getPath;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.androidffmpeg.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class EditFragment extends Fragment {
    private static int REQUEST_CODE_FOR_DIR=1;

    private View view;
    private String args = null;
    private TextView mVideo_Text, mAudio_Text;
    private Button mVideo_Button, mAudio_Button;

    String path =null;
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
        mVideo_Button = view.findViewById(R.id.video_button);
        mAudio_Button = view.findViewById(R.id.audio_button);
        //intoFileManager();
        GetVideoFile();
    }

    /**
     * 按钮函数
     */
    private void GetVideoFile() {
        mVideo_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntoFileManager();
            }
        });
    }

    /**
     * 打开文件夹
     **/
    private void IntoFileManager() {
        //GetPermission_data(getActivity());

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//筛选器
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.putExtra(EXTRA_ALLOW_MULTIPLE, true);//设置多选
        startActivityForResult(Intent.createChooser(intent,"选择一个文件"),1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                Uri uri = data.getData();//获取URI
                StringBuilder result = new StringBuilder();//保存读取到的内容
                InputStream is = getContext().getContentResolver().openInputStream(uri);//获取输入流
                //创建用于字符输入流中读取文本的bufferReader对象
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    //将读取到的内容放入结果字符串
                    result.append(line);
                }
                //文件中的内容
                String content = is.toString();
                Toast.makeText(getActivity(),"获取的文件内容:"+content,Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


//    /**
//     * 获取data权限
//     */
//    public void GetPermission_data(Activity activity) {
//        Uri uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata");
//        DocumentFile documentFile = DocumentFile.fromTreeUri(activity, uri);
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
//                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
//                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
//        //assert documentFile != null;
//        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, documentFile.getUri());
//        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
//        activity.startActivityForResult(intent, REQUEST_CODE_FOR_DIR);
//    }
//
//    @SuppressLint("WrongConstant")
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Uri uri;
//        if (requestCode == REQUEST_CODE_FOR_DIR && (uri = data.getData()) != null) {
//            getActivity().getContentResolver().takePersistableUriPermission(uri, data.getFlags()&
//                    (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
//        }
//    }
}
