package com.ramzmania.aicammvd.ui.base


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A base activity class for activities that use view binding and view models.
 *
 * @param VBinding The type of view binding used by the activity.
 * @param ViewModel The type of view model associated with the activity.
 */

abstract class BaseBinderActivity<VBinding : ViewBinding, ViewModel : BaseViewModel> : ComponentActivity() {

    protected lateinit var viewModel: ViewModel
    protected abstract fun getViewModelClass(): Class<ViewModel>
    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

   // private lateinit var progressDialog : ProgressDialog
    var builder:AlertDialog.Builder?=null
    var dialog: Dialog? = null
    /**
     * Observes the view model's live data.
     */
    abstract fun observeViewModel()
    /**
     * Observes activity-specific events or states.
     */
    abstract fun observeActivity()
    /**
     * Additional setup for observing activity with instance state.
     */
    open fun observeActivityWithInstance(savedInstanceState: Bundle?){}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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


    /**
     * Sets up views or performs additional initialization.
     */
    open fun setUpViews() {}
    /**
     * Observes views for any updates or changes.
     */
    open fun observeView() {}


    /**
     * Hides the progress dialog if shown.
     */
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