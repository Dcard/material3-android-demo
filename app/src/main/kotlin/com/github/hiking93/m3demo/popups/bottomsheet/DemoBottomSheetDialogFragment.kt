package com.github.hiking93.m3demo.popups.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.hiking93.m3demo.databinding.FragmentDemoBottomSheetDialogBinding
import com.github.hiking93.m3demo.shared.ViewBindingBottomSheetDialogFragment

class DemoBottomSheetDialogFragment :
    ViewBindingBottomSheetDialogFragment<FragmentDemoBottomSheetDialogBinding>() {

    interface Interaction {
        fun onOptionClick(option: DemoBottomSheetDialogAdapterItem.Option)
    }

    private val adapter by lazy { DemoBottomSheetDialogAdapter(adapterInteraction) }

    private val adapterInteraction = object : DemoBottomSheetDialogAdapter.Interaction {

        override fun onOptionClick(option: DemoBottomSheetDialogAdapterItem.Option) {
            interaction?.onOptionClick(option)
            dismiss()
        }
    }

    private val interaction: Interaction?
        get() = parentFragment as? Interaction ?: context as? Interaction

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDemoBottomSheetDialogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        binding.recyclerView.adapter = adapter
    }
}