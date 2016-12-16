//
// Created by liu on 2016/12/16 0016.
//

#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_liu_chapter14_MainActivity_stringFromJNI(JNIEnv *env, jobject instance) {

    // TODO
    jstring str = "Hello Java it's from C";

    return (*env)->NewStringUTF(env, str);
}

JNIEXPORT jint JNICALL
Java_com_example_liu_chapter14_MainActivity_max(JNIEnv *env, jobject instance, jint a, jint b) {

    if (a > b) {
        return a;
    } else {
        return b;
    }
}

JNIEXPORT void JNICALL
Java_com_example_liu_chapter14_MainActivity_callJavaStaticMethod(JNIEnv *env, jobject instance) {
    jclass clazz = (*env)->FindClass(env, "com/example/liu/chapter14/MainActivity");

    jmethodID jmethodID1 = (*env)->GetStaticMethodID(env, clazz, "javaStaticMethod",
                                                     "(Ljava/lang/String;)V");
    jstring str = (*env)->NewStringUTF(env, "Hello call Java static method");
    (*env)->CallStaticVoidMethod(env, clazz, jmethodID1, str);
}

JNIEXPORT void JNICALL
Java_com_example_liu_chapter14_MainActivity_callJavaMethod(JNIEnv *env, jobject instance) {
    jclass clazz = (*env)->FindClass(env, "com/example/liu/chapter14/MainActivity");
    jmethodID method = (*env)->GetMethodID(env, clazz, "javaMethod", "(Ljava/lang/String;)V");
    jstring str = (*env)->NewStringUTF(env, "call java method");
    (*env)->CallVoidMethod(env, instance, method, str);
}

JNIEXPORT jstring JNICALL
Java_com_example_liu_chapter14_KotlinActivity_kotlinGetString(JNIEnv *env, jobject instance) {
    jstring str = (*env)->NewStringUTF(env, "Hello Kotlin It's From C");
    return str;
}

//JNIEXPORT jstring JNICALL
//Java_com_example_liu_chapter14_KotlinActivity_callKotlinMethod(JNIEnv *env, jobject instance) {
//    jclass clazz = (*env)->FindClass(env, "com/example/liu/chapter14/KotlinActivity");
//    jmethodID method = (*env)->GetMethodID(env, clazz, "kotlinMethod", "Ljava/lang/String;)V");
//    jstring str = (*env)->NewStringUTF(env, "Hello Kotlin call kotlin method");
//    (*env)->CallVoidMethod(env, instance, method, str);
//}