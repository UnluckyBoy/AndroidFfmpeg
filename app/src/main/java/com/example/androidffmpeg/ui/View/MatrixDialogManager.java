package com.example.androidffmpeg.ui.View;

import android.app.Activity;
import android.content.Intent;

/**
 * @ClassName MatrixDialogManager
 * @Author Create By Administrator
 * @Date 2023/4/15 0015 14:48
 */
public class MatrixDialogManager {
    public MatrixDialogManager(){
    }

    /**显示窗口**/
    public <T> void ShowMatrixDialog(String[] names, Activity currentTGA, Class<T> targetTGA){
        /**MatrixDialog中最后两个按钮的顺序与names的文本顺序相反**/
        MatrixDialog mDialog = new MatrixDialog(currentTGA, names, true);
        mDialog.setOnClickListener2LastTwoItems(new MatrixDialog.OnClickListener2LastTwoItem() {
            /**取消按钮**/
            @Override
            public void onClickListener2LastItem() {
                mDialog.dismiss();
            }
            /**确定按钮**/
            @Override
            public void onClickListener2SecondLastItem() {
                //Class<MainActivity> tset=MainActivity.class;
                Intent main_Intent=new Intent(currentTGA, targetTGA);
                main_Intent.putExtra("U_account","");
                currentTGA.startActivity(main_Intent);
                currentTGA.finish();
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
