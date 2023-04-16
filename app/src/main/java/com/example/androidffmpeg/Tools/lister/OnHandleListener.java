package com.example.androidffmpeg.Tools.lister;

/**
 * @ClassName OnHandleListener
 * @Author Create By Administrator
 * @Date 2023/4/17 0017 0:04
 */
public interface OnHandleListener {
    void onBegin();
    void onMsg(String msg);
    void onProgress(int progress,int duration);
    void onEnd(int resultCode,String resultMsg);
}
