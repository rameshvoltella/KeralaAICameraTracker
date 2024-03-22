package com.ramzmania.aicammvd.ui.base


import android.app.AlertDialog
import android.app.Dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint


abstract class BaseFragment<VBinding : ViewBinding, ViewModel : BaseViewModel> : Fragment() {

    open var useSharedViewModel: Boolean = false

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>

    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    var builder: AlertDialog.Builder?=null

    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeView()
        observeData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    open fun setUpViews() {}

    open fun observeView() {}

    open fun observeData() {}

    private fun init() {
        binding = getViewBinding()
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity())[getViewModelClass()]
        } else {
            ViewModelProvider(this)[getViewModelClass()]
        }
    }



    open fun hideProgressDialog()
    {
        try {
            if (dialog != null) {
                dialog?.dismiss()
            }
        }catch (ex:Exception)
        {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }



}
