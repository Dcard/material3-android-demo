package com.github.hiking93.m3demo.typography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.github.hiking93.m3demo.databinding.FragmentTypographyBinding
import com.github.hiking93.m3demo.shared.ViewBindingFragment
import com.github.hiking93.m3demo.shared.ui.doOnWindowInsetsChanged
import com.github.hiking93.m3demo.shared.ui.dpToPxSize

class TypographyFragment : ViewBindingFragment<FragmentTypographyBinding>() {

    companion object {
        fun newInstance() = TypographyFragment()
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentTypographyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWindow()
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
            binding.contentLayout.updatePadding(
                bottom = systemWindowInsets.bottom + 16f.dpToPxSize(v.context)
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
}