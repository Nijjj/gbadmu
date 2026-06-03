package app.y2kboy.nativebridge

import android.view.Surface

class EmulatorBridge {
    val coreVersion: String
        get() = nativeCoreVersion()

    fun attachSurface(surface: Surface) = nativeAttachSurface(surface)
    fun loadGame(path: String): Boolean = nativeLoadGame(path)
    fun pause() = nativePause()
    fun resume() = nativeResume()
    fun reset() = nativeReset()
    fun setInput(inputMask: Int) = nativeSetInput(inputMask)
    fun saveState(slot: Int): Boolean = nativeSaveState(slot)
    fun loadState(slot: Int): Boolean = nativeLoadState(slot)
    fun captureScreenshot(): Boolean = nativeCaptureScreenshot()
    fun setFastForward(enabled: Boolean, cap: Float) = nativeSetFastForward(enabled, cap)

    private external fun nativeCoreVersion(): String
    private external fun nativeAttachSurface(surface: Surface)
    private external fun nativeLoadGame(path: String): Boolean
    private external fun nativePause()
    private external fun nativeResume()
    private external fun nativeReset()
    private external fun nativeSetInput(inputMask: Int)
    private external fun nativeSaveState(slot: Int): Boolean
    private external fun nativeLoadState(slot: Int): Boolean
    private external fun nativeCaptureScreenshot(): Boolean
    private external fun nativeSetFastForward(enabled: Boolean, cap: Float)

    companion object {
        init {
            System.loadLibrary("y2kboy")
        }
    }
}
