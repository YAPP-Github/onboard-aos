import android.graphics.Rect
import android.view.ViewTreeObserver
import android.view.Window

class KeyboardVisibilityUtils(
    private val window: Window,
    private val onShowKeyboard: ((keyboardHeight: Int) -> Unit)? = null,
    private val onHideKeyboard: (() -> Unit)? = null
) {

    private val MIN_KEYBOARD_HEIGHT_PX = 150

    private val windowVisibleDisplayFrame = Rect()
    private var lastVisibleDecorViewHeight: Int = 0

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        window.decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
        val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

        if (lastVisibleDecorViewHeight != 0) {
            if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                val currentKeyboardHeight = window.decorView.height - windowVisibleDisplayFrame.bottom
                onShowKeyboard?.let { it(currentKeyboardHeight) }
            } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                onHideKeyboard?.let { it() }
            }
        }
        lastVisibleDecorViewHeight = visibleDecorViewHeight
    }

    init {
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun detachKeyboardListeners() {
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}
