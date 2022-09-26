package com.github.hiking93.m3demo.shared

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AlertDialogFragment : DialogFragment() {

    companion object {

        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_MESSAGE = "ARG_MESSAGE"
        private const val ARG_ICON = "ARG_ICON"
        private const val ARG_POSITIVE_BUTTON = "ARG_POSITIVE_BUTTON"
        private const val ARG_NEUTRAL_BUTTON = "ARG_NEUTRAL_BUTTON"
        private const val ARG_NEGATIVE_BUTTON = "ARG_NEGATIVE_BUTTON"
        private const val ARG_CANCELED_ON_TOUCH_OUTSIDE = "ARG_CANCELED_ON_TOUCH_OUTSIDE"

        const val ACTION_DISMISS = 0
        const val ACTION_CANCEL = 1
        const val ACTION_POSITIVE = 10
        const val ACTION_NEUTRAL = 11
        const val ACTION_NEGATIVE = 12
    }

    class Builder(val context: Context) {

        private var title: CharSequence? = null
        private var message: CharSequence? = null
        private var iconResId: Int? = null
        private var positiveButton: CharSequence? = null
        private var neutralButton: CharSequence? = null
        private var negativeButton: CharSequence? = null
        private var isCancelable = true
        private var cancelOnTouchOutside = true

        fun create() = AlertDialogFragment().apply {
            val builder = this@Builder
            title = builder.title
            message = builder.message
            iconResId = builder.iconResId
            positiveButton = builder.positiveButton
            neutralButton = builder.neutralButton
            negativeButton = builder.negativeButton
            isCancelable = builder.isCancelable
            cancelOnTouchOutside = builder.cancelOnTouchOutside
        }

        fun show(manager: FragmentManager, tag: String? = null) {
            create().show(manager, tag)
        }

        fun setTitle(title: CharSequence?) = apply {
            this.title = title
        }

        fun setTitle(@StringRes title: Int) = apply {
            this.title = context.getText(title)
        }

        fun setMessage(message: CharSequence?) = apply {
            this.message = message
        }

        fun setMessage(@StringRes message: Int) = apply {
            this.message = context.getText(message)
        }

        fun setIcon(@DrawableRes resId: Int) = apply {
            this.iconResId = resId
        }

        fun setPositiveButton(text: CharSequence?) = apply {
            this.positiveButton = text
        }

        fun setPositiveButton(@StringRes text: Int) = apply {
            this.positiveButton = context.getText(text)
        }

        fun setNeutralButton(text: CharSequence?) = apply {
            this.neutralButton = text
        }

        fun setNeutralButton(@StringRes text: Int) = apply {
            this.neutralButton = context.getText(text)
        }

        fun setNegativeButton(text: CharSequence?) = apply {
            this.negativeButton = text
        }

        fun setNegativeButton(@StringRes text: Int) = apply {
            this.negativeButton = context.getText(text)
        }

        fun setCancellable(isCancellable: Boolean) = apply {
            this.isCancelable = isCancellable
        }

        fun setCancelOnTouchOutside(cancel: Boolean) = apply {
            this.cancelOnTouchOutside = cancel
        }
    }

    interface Interaction {
        fun onDialogAction(fragment: AlertDialogFragment, @Action action: Int)
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        ACTION_DISMISS,
        ACTION_POSITIVE,
        ACTION_NEUTRAL,
        ACTION_NEGATIVE,
        ACTION_CANCEL,
    )
    annotation class Action

    private var title
        set(value) = putArguments(Bundle().apply { putCharSequence(ARG_TITLE, value) })
        get() = arguments?.getCharSequence(ARG_TITLE)

    private var message
        set(value) = putArguments(Bundle().apply { putCharSequence(ARG_MESSAGE, value) })
        get() = arguments?.getCharSequence(ARG_MESSAGE)

    private var iconResId
        set(value) = putArguments(Bundle().apply { putInt(ARG_ICON, value ?: 0) })
        get() = arguments?.getInt(ARG_ICON)?.takeIf { it != 0 }

    private var positiveButton
        set(value) = putArguments(Bundle().apply { putCharSequence(ARG_POSITIVE_BUTTON, value) })
        get() = arguments?.getCharSequence(ARG_POSITIVE_BUTTON)

    private var neutralButton
        set(value) = putArguments(Bundle().apply { putCharSequence(ARG_NEUTRAL_BUTTON, value) })
        get() = arguments?.getCharSequence(ARG_NEUTRAL_BUTTON)

    private var negativeButton
        set(value) = putArguments(Bundle().apply { putCharSequence(ARG_NEGATIVE_BUTTON, value) })
        get() = arguments?.getCharSequence(ARG_NEGATIVE_BUTTON)

    private var cancelOnTouchOutside
        set(value) = putArguments(Bundle().apply {
            putBoolean(
                ARG_CANCELED_ON_TOUCH_OUTSIDE,
                value
            )
        })
        get() = arguments?.getBoolean(ARG_CANCELED_ON_TOUCH_OUTSIDE, true) ?: true

    private val interaction: Interaction?
        get() = parentFragment as? Interaction ?: context as? Interaction

    override fun onCreateDialog(savedInstanceState: Bundle?) = run {
        val fragment = this@AlertDialogFragment
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(title)
            setMessage(message)
            iconResId?.let {
                setIcon(it)
            }
            positiveButton?.let {
                setPositiveButton(it) { _, _ ->
                    interaction?.onDialogAction(fragment, ACTION_POSITIVE)
                }
            }
            neutralButton?.let {
                setNeutralButton(it) { _, _ ->
                    interaction?.onDialogAction(fragment, ACTION_NEUTRAL)
                }
            }
            negativeButton?.let {
                setNegativeButton(it) { _, _ ->
                    interaction?.onDialogAction(fragment, ACTION_NEGATIVE)
                }
            }
        }.create().apply {
            if (isCancelable) {
                setCanceledOnTouchOutside(cancelOnTouchOutside)
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        interaction?.onDialogAction(this, ACTION_CANCEL)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        interaction?.onDialogAction(this, ACTION_DISMISS)
    }
}