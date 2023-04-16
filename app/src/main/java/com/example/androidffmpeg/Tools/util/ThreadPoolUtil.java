package com.example.androidffmpeg.Tools.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName ThreadPoolUtil
 * @Author Create By Administrator
 * @Date 2023/4/17 0017 0:07
 */
public class ThreadPoolUtil {
    public static ThreadPoolUtil INSTANCE = null;

    private ThreadPoolUtil() {
        INSTANCE = (ThreadPoolUtil)this;
    }



    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public ExecutorService executeSingleThreadPool(Runnable runnable){
        executor.submit(runnable);
        return executor;
    }

}
