package com.github.hiking93.m3demo.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.hiking93.m3demo.databinding.FragmentWidgetsBinding
import com.github.hiking93.m3demo.shared.ViewBindingFragment
import com.github.hiking93.m3demo.shared.doOnWindowInsetsChanged
import com.google.android.material.divider.MaterialDividerItemDecoration

class WidgetsFragment : ViewBindingFragment<FragmentWidgetsBinding>() {

    companion object {
        fun newInstance() = WidgetsFragment()
    }

    private val adapter = WidgetsAdapter()

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentWidgetsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWindow()
        setupViews()
    }

    private fun setupWindow() {
        binding.root.doOnWindowInsetsChanged(
            listenToAnimation = true,
        ) { _, insets ->
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
                bottom = systemWindowInsets.bottom,
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
            adapter = this@WidgetsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}