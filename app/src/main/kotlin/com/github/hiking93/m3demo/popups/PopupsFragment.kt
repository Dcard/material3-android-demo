package com.github.hiking93.m3demo.popups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hiking93.m3demo.R
import com.github.hiking93.m3demo.databinding.FragmentPopupsBinding
import com.github.hiking93.m3demo.shared.AlertDialogFragment
import com.github.hiking93.m3demo.shared.ViewBindingFragment
import com.github.hiking93.m3demo.shared.doOnWindowInsetsChanged
import com.github.hiking93.m3demo.shared.dpToPxSize
import com.google.android.material.snackbar.Snackbar

class PopupsFragment : ViewBindingFragment<FragmentPopupsBinding>(),
    AlertDialogFragment.Interaction {

    companion object {

        private const val FRAGMENT_TAG_DIALOG_BASIC = "FRAGMENT_TAG_DIALOG_BASIC"
        private const val FRAGMENT_TAG_DIALOG_BASIC_WITH_BUTTONS =
            "FRAGMENT_TAG_DIALOG_BASIC_WITH_BUTTONS"

        fun newInstance() = PopupsFragment()
    }

    private val adapter by lazy { PopupsAdapter(adapterInteraction) }

    private val adapterInteraction by lazy {
        object : PopupsAdapter.Interaction {
            override fun onPopupOptionClick(option: PopupsAdapterItem.PopupOption) {
                showPopup(option)
            }
        }
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPopupsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWindow()
        setupViews()
    }

    override fun onDialogAction(fragment: AlertDialogFragment, action: Int) {
        when (fragment.tag) {
            FRAGMENT_TAG_DIALOG_BASIC,
            FRAGMENT_TAG_DIALOG_BASIC_WITH_BUTTONS -> {
                when (action) {
                    AlertDialogFragment.ACTION_POSITIVE -> {
                        R.string.popups_dialog_action_positive_arg
                    }
                    AlertDialogFragment.ACTION_NEGATIVE -> {
                        R.string.popups_dialog_action_negative_arg
                    }
                    AlertDialogFragment.ACTION_NEUTRAL -> {
                        R.string.popups_dialog_action_neutral_arg
                    }
                    AlertDialogFragment.ACTION_CANCEL -> {
                        R.string.popups_dialog_action_cancel_arg
                    }
                    else -> null
                }?.let { argStringResId ->
                    getString(
                        R.string.popups_dialog_action_message_format,
                        getString(argStringResId),
                    )
                }?.let { message ->
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupWindow() {
        binding.root.doOnWindowInsetsChanged { v, insets ->
            val systemWindowInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime(),
            )
            binding.root.updatePadding(
                left = systemWindowInsets.left,
                right = systemWindowInsets.right,
            )
            binding.appBarLayout.updatePadding(
                top = systemWindowInsets.top,
            )
            binding.recyclerView.updatePadding(
                bottom = systemWindowInsets.bottom + 8f.dpToPxSize(v.context)
            )
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            WindowInsetsCompat.Builder(insets)
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(),
                    Insets.of(0, 0, 0, systemBarInsets.bottom)
                )
                .setInsets(
                    WindowInsetsCompat.Type.ime(),
                    Insets.of(0, 0, 0, imeInsets.bottom)
                )
                .build()
        }
    }

    private fun setupViews() {
        binding.recyclerView.apply {
            adapter = this@PopupsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun showPopup(
        option: PopupsAdapterItem.PopupOption,
    ) {
        when (option.type) {
            PopupsAdapterItem.PopupOption.Type.BasicDialog -> {
                AlertDialogFragment.Builder(requireContext())
                    .setTitle(R.string.popups_dialog_basic_title)
                    .setMessage(R.string.popups_dialog_basic_message)
                    .setPositiveButton(R.string.popups_dialog_ok_action)
                    .show(childFragmentManager, FRAGMENT_TAG_DIALOG_BASIC)
            }
            PopupsAdapterItem.PopupOption.Type.BasicDialogWithButtons -> {
                AlertDialogFragment.Builder(requireContext())
                    .setTitle(R.string.popups_dialog_basic_title)
                    .setMessage(R.string.popups_dialog_basic_message)
                    .setPositiveButton(R.string.popups_dialog_positive_action)
                    .setNegativeButton(R.string.popups_dialog_negative_action)
                    .setNeutralButton(R.string.popups_dialog_neutral_action)
                    .show(childFragmentManager, FRAGMENT_TAG_DIALOG_BASIC_WITH_BUTTONS)
            }
            PopupsAdapterItem.PopupOption.Type.Snackbar -> {
                Snackbar
                    .make(
                        binding.root,
                        R.string.popups_snackbar_default_message,
                        Snackbar.LENGTH_SHORT,
                    )
                    .show()
            }
            PopupsAdapterItem.PopupOption.Type.SnackbarWithAction -> {
                Snackbar
                    .make(
                        binding.root,
                        R.string.popups_snackbar_default_message,
                        Snackbar.LENGTH_SHORT,
                    )
                    .setAction(R.string.popups_snackbar_action) {
                        Snackbar
                            .make(
                                binding.root,
                                R.string.popups_snackbar_action_clicked_message,
                                Snackbar.LENGTH_SHORT,
                            )
                            .show()
                    }
                    .show()
            }
        }
    }
}