package com.github.hiking93.m3demo.popups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.github.hiking93.m3demo.R
import com.github.hiking93.m3demo.databinding.FragmentPopupsBinding
import com.github.hiking93.m3demo.popups.bottomsheet.DemoBottomSheetDialogAdapterItem
import com.github.hiking93.m3demo.popups.bottomsheet.DemoBottomSheetDialogFragment
import com.github.hiking93.m3demo.shared.AlertDialogFragment
import com.github.hiking93.m3demo.shared.ViewBindingFragment
import com.github.hiking93.m3demo.shared.doOnWindowInsetsChanged
import com.github.hiking93.m3demo.shared.dpToPxSize
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker

class PopupsFragment : ViewBindingFragment<FragmentPopupsBinding>(),
    AlertDialogFragment.Interaction,
    DemoBottomSheetDialogFragment.Interaction {

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

    override fun onOptionClick(option: DemoBottomSheetDialogAdapterItem.Option) {
        Snackbar.make(
            binding.root,
            getString(
                R.string.popups_dialog_bottom_sheet_option_selected_message_format,
                option.serialNumber,
            ),
            Snackbar.LENGTH_SHORT,
        ).show()
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
        binding.toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.app_bar_action -> {
                    Snackbar
                        .make(
                            binding.root,
                            R.string.popups_menu_action_clicked_message,
                            Snackbar.LENGTH_SHORT,
                        )
                        .show()
                    true
                }
                R.id.app_bar_overflow_1,
                R.id.app_bar_overflow_2,
                R.id.app_bar_overflow_3 -> {
                    Snackbar
                        .make(
                            binding.root,
                            R.string.popups_menu_overflow_action_clicked_message,
                            Snackbar.LENGTH_SHORT,
                        )
                        .show()
                    true
                }
                else -> false
            }
        }
        binding.recyclerView.adapter = adapter
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
            PopupsAdapterItem.PopupOption.Type.BottomSheetDialog -> {
                DemoBottomSheetDialogFragment().show(childFragmentManager, null)
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
            PopupsAdapterItem.PopupOption.Type.DatePicker -> {
                MaterialDatePicker.Builder.datePicker().build()
                    .show(childFragmentManager, null)
            }
            PopupsAdapterItem.PopupOption.Type.DateRangePicker -> {
                MaterialDatePicker.Builder.dateRangePicker().build()
                    .show(childFragmentManager, null)
            }
            PopupsAdapterItem.PopupOption.Type.TimePicker -> {
                MaterialTimePicker.Builder()
                    .setTitleText(R.string.popups_time_picker_title)
                    .build()
                    .show(childFragmentManager, null)
            }
        }
    }
}