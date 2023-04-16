#include <jni.h>
#include <string>
#include <libavcodec/jni.h>

extern "C"{
#include <libavcodec/avcodec.h>
}

/**
 * 调用C++
 * Ffmpeg动态库测试
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_androidffmpeg_ui_Fragment_MainFragment_stringFromJNI(JNIEnv *env, jobject thiz) {
    // TODO: implement stringFromJNI()
    std::string ffmpegStr=avcodec_configuration();
    std::string hello = "Hello from C++\n"+ffmpegStr;
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_androidffmpeg_ui_Fragment_EditFragment_getVersion(JNIEnv *env, jobject thiz) {
    // TODO: implement getVersion()
    return env->NewStringUTF(av_version_info());
}