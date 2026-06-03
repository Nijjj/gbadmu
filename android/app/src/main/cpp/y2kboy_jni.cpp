#include <android/log.h>
#include <android/native_window_jni.h>
#include <jni.h>

#include <mutex>
#include <string>

namespace {
constexpr char kTag[] = "Y2KBoy";

struct EmulatorRuntime {
    ANativeWindow* window = nullptr;
    bool loaded = false;
    bool paused = true;
    float fast_forward_cap = 1.0f;
    int input_mask = 0;
    std::string game_path;
};

EmulatorRuntime& Runtime() {
    static EmulatorRuntime runtime;
    return runtime;
}

std::mutex& RuntimeMutex() {
    static std::mutex runtime_mutex;
    return runtime_mutex;
}

void Log(const char* message) {
    __android_log_print(ANDROID_LOG_INFO, kTag, "%s", message);
}
}  // namespace

extern "C" JNIEXPORT jstring JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeCoreVersion(JNIEnv* env, jobject /*thiz*/) {
#if defined(Y2KBOY_ENABLE_REAL_MGBA)
    return env->NewStringUTF("mGBA-integrated");
#else
    return env->NewStringUTF("scaffold-stub");
#endif
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeAttachSurface(JNIEnv* env, jobject /*thiz*/, jobject surface) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    auto& runtime = Runtime();
    if (runtime.window != nullptr) {
        ANativeWindow_release(runtime.window);
        runtime.window = nullptr;
    }
    if (surface != nullptr) {
        runtime.window = ANativeWindow_fromSurface(env, surface);
        Log("Surface attached");
    }
}

extern "C" JNIEXPORT jboolean JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeLoadGame(JNIEnv* env, jobject /*thiz*/, jstring path) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    auto& runtime = Runtime();
    const char* c_path = env->GetStringUTFChars(path, nullptr);
    if (c_path == nullptr) {
        return JNI_FALSE;
    }
    runtime.game_path = c_path == nullptr ? "" : c_path;
    env->ReleaseStringUTFChars(path, c_path);
    runtime.loaded = !runtime.game_path.empty();
    runtime.paused = !runtime.loaded;
    Log("Game load requested");
    return runtime.loaded ? JNI_TRUE : JNI_FALSE;
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativePause(JNIEnv* /*env*/, jobject /*thiz*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    Runtime().paused = true;
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeResume(JNIEnv* /*env*/, jobject /*thiz*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    if (Runtime().loaded) {
        Runtime().paused = false;
    }
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeReset(JNIEnv* /*env*/, jobject /*thiz*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    Runtime().input_mask = 0;
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeSetInput(JNIEnv* /*env*/, jobject /*thiz*/, jint input_mask) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    Runtime().input_mask = input_mask;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeSaveState(JNIEnv* /*env*/, jobject /*thiz*/, jint /*slot*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    return Runtime().loaded ? JNI_TRUE : JNI_FALSE;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeLoadState(JNIEnv* /*env*/, jobject /*thiz*/, jint /*slot*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    return Runtime().loaded ? JNI_TRUE : JNI_FALSE;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeCaptureScreenshot(JNIEnv* /*env*/, jobject /*thiz*/) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    return Runtime().loaded ? JNI_TRUE : JNI_FALSE;
}

extern "C" JNIEXPORT void JNICALL
Java_app_y2kboy_nativebridge_EmulatorBridge_nativeSetFastForward(
    JNIEnv* /*env*/,
    jobject /*thiz*/,
    jboolean enabled,
    jfloat cap
) {
    std::lock_guard<std::mutex> lock(RuntimeMutex());
    Runtime().fast_forward_cap = enabled ? cap : 1.0f;
}
