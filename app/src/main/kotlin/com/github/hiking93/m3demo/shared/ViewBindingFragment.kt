package com.github.hiking93.m3demo.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T : ViewBinding> : Fragment() {

    protected val binding: T
        get() = _binding ?: throw ViewBindingNotAvailableException(
            "View binding is only available between onCreateView() and onDestroyView()."
        )
    private var _binding: T? = null

    abstract fun onCreateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = onCreateViewBinding(inflater, container, savedInstanceState).let {
        _binding = it
        it.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}