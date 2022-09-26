package com.github.hiking93.m3demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.github.hiking93.m3demo.colors.ColorsFragment
import com.github.hiking93.m3demo.databinding.ActivityMainBinding
import com.github.hiking93.m3demo.popups.PopupsFragment
import com.github.hiking93.m3demo.shared.ViewBindingActivity
import com.github.hiking93.m3demo.shared.applyEdgeToEdge
import com.github.hiking93.m3demo.shared.doOnWindowInsetsChanged
import com.github.hiking93.m3demo.typography.TypographyFragment
import com.github.hiking93.m3demo.widgets.WidgetsFragment

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {

    companion object {
        private const val SAVED_STATE_SELECTED_ITEM_ID = "SAVED_STATE_SELECTED_ITEM_ID"
    }

    private var selectedItemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreStates(savedInstanceState)
        setupWindow()
        setupViews()
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) == null) {
            switchPage(R.id.widgets)
        }
    }

    private fun restoreStates(savedInstanceState: Bundle?) {
        selectedItemId =
            savedInstanceState?.getInt(SAVED_STATE_SELECTED_ITEM_ID)?.takeIf { it != 0 }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_STATE_SELECTED_ITEM_ID, selectedItemId ?: 0)
    }

    override fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ActivityMainBinding.inflate(inflater, container, false)

    private fun setupWindow() {
        applyEdgeToEdge()
        binding.root.doOnWindowInsetsChanged { _, insets ->
            val systemWindowInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime(),
            )
            binding.root.updatePadding(
                left = systemWindowInsets.left,
                right = systemWindowInsets.right,
            )
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            WindowInsetsCompat.Builder(insets)
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(),
                    Insets.of(0, systemBarInsets.top, 0, systemBarInsets.bottom)
                )
                .setInsets(
                    WindowInsetsCompat.Type.ime(),
                    Insets.of(0, imeInsets.top, 0, imeInsets.bottom)
                )
                .build()
        }
        binding.fragmentContainerView.doOnWindowInsetsChanged { _, insets ->
            val hasBottomNavigationBar = binding.bottomNavigationView != null
            val systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBarBottomInset = if (hasBottomNavigationBar) 0 else systemBarInsets.bottom
            val imeBottomInset = if (hasBottomNavigationBar) 0 else imeInsets.bottom
            WindowInsetsCompat.Builder(insets)
                .setInsets(
                    WindowInsetsCompat.Type.systemBars(),
                    Insets.of(0, systemBarInsets.top, 0, systemBarBottomInset)
                )
                .setInsets(
                    WindowInsetsCompat.Type.ime(),
                    Insets.of(0, imeInsets.top, 0, imeBottomInset)
                )
                .build()
        }
    }

    private fun setupViews() {
        val selectedItemId = selectedItemId
        binding.navigationRail?.apply {
            selectedItemId?.let { setSelectedItemId(it) }
            setOnItemSelectedListener { item ->
                switchPage(item.itemId)
            }
        }
        binding.bottomNavigationView?.apply {
            selectedItemId?.let { setSelectedItemId(it) }
            setOnItemSelectedListener { item ->
                switchPage(item.itemId)
            }
        }
    }

    private fun switchPage(itemId: Int): Boolean {
        selectedItemId = itemId
        val fragment = when (itemId) {
            R.id.widgets -> WidgetsFragment.newInstance()
            R.id.popups -> PopupsFragment.newInstance()
            R.id.typography -> TypographyFragment.newInstance()
            R.id.colors -> ColorsFragment.newInstance()
            else -> null
        }
        return fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit()
            true
        } ?: false
    }
}