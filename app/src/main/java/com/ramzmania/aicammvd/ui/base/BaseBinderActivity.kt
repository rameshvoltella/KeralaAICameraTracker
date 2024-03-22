package com.ramzmania.aicammvd.ui.base


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint



abstract class BaseBinderActivity<VBinding : ViewBinding, ViewModel : BaseViewModel> : ComponentActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>
    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

   // private lateinit var progressDialog : ProgressDialog
    var builder:AlertDialog.Builder?=null
    var dialog: Dialog? = null

    abstract fun observeViewModel()
    abstract fun observeActivity()
    open fun observeActivityWithInstance(savedInstanceState: Bundle?){}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Log.e("iniiiiiiit", "iiiiiiiiiiii")
        init()
        setContentView(binding.root)
        setUpViews()
        observeViewModel()
        observeActivityWithInstance(savedInstanceState)
        observeActivity()
    }
    private fun init() {
        binding = getViewBinding()
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        //progressDialog = ProgressDialog(this, R.style.MyDialogStyle)
    }



    open fun setUpViews() {}

    open fun observeView() {}



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

    /*  use when localize completely
    @Override
    protected void attachBaseContext(Context context) {
        ContextWrapper localeContextWrapper  = LocaleContextWrapper.wrap(context, PreferenceManager.getDefaultSharedPreferences(context).getString(context.getString(R.string.locale_lang), "en"));
        ContextWrapper calligraphyContextWrapper = CalligraphyContextWrapper.wrap(localeContextWrapper);
        super.attachBaseContext(calligraphyContextWrapper);
    }*/




}