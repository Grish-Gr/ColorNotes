package com.example.colornotes.view.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import com.example.colornotes.R
import com.example.colornotes.view.view.filter.FilterSetting
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseFragment: Fragment() {

    protected fun openFragment(fragment: Fragment, parcelable: Parcelable? = null){
        if (parcelable != null){
            val bundle = Bundle()
            bundle.putParcelable(KEY_PUT_DATA, parcelable)
            fragment.arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .addToBackStack(TAG_DEFAULT_BACK_STACK)
            .replace(R.id.main_container_fragment, fragment)
            .commit()
    }

    protected fun openBottomFragment(
            fragment: BottomSheetDialogFragment,
            parcelable: Parcelable,
            listenerResult: (key: String, bundle: Bundle) -> Unit) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_PUT_DATA, parcelable)
        fragment.arguments = bundle
        fragment.show(parentFragmentManager, TAG_DEFAULT_SHOW_FRAGMENT)
        fragment.setFragmentResultListener(KEY_RESULT_FRAGMENT) { key, bundle ->
            listenerResult(key, bundle)
        }
    }

    protected fun backToParentFragment(){
        parentFragmentManager.popBackStack(
            TAG_DEFAULT_BACK_STACK, FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    protected fun changeThemeApp(){
        if (isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        saveTheme()
    }

    @SuppressLint("CommitPrefEdits")
    protected fun getSaveFilterSetting(): FilterSetting{
        // TODO Add Default value!
        val sharedPreference = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val filterSorting = sharedPreference?.getInt(KEY_EDIT_FILTER_SORTING, 0) ?: 0
        val filterView = sharedPreference?.getInt(KEY_EDIT_FILTER_VIEW, 0) ?: 0
        val saveGroup = sharedPreference?.getLong(KEY_EDIT_FILTER_GROUP, -1L)
        val filterGroup = if (saveGroup == -1L) null
                          else saveGroup
        Log.e("TAG", "GET: Sorting: $filterSorting; View: $filterView; Group: $filterGroup")
        return FilterSetting(filterSorting, filterView, filterGroup)
    }

    @SuppressLint("CommitPrefEdits")
    protected fun saveFilterSetting(filterSetting: FilterSetting){
        val sharedPreference = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val edit = sharedPreference?.edit()
        with(filterSetting){
            Log.e("TAG", this.toString())
            edit?.putInt(KEY_EDIT_FILTER_SORTING, ordinalSortFilter)
            edit?.putInt(KEY_EDIT_FILTER_VIEW, ordinalViewFilter)
            edit?.putLong(KEY_EDIT_FILTER_GROUP, filterGroup ?: -1)
            edit?.apply()
        }
    }

    @SuppressLint("CommitPrefEdits")
    protected fun saveTheme(){
        val sharedPreference = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val edit = sharedPreference?.edit()
        edit?.putBoolean(KEY_EDIT_IS_NIGHT_MODE, !isNightMode())
        edit?.apply()
    }

    private fun isNightMode(): Boolean = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }

    companion object{
        const val KEY_PUT_DATA = "DefaultTagPutData"
        const val KEY_RESULT_FRAGMENT = "DefaultKeyResultFragment"
        const val TAG_DEFAULT_BACK_STACK = "DefaultTagBackStack"
        const val TAG_DEFAULT_SHOW_FRAGMENT = "DefaultTagShowFragment"

        const val NAME_SHARED_PREFERENCE = "DefaultNameSharedPreference"
        const val KEY_EDIT_IS_NIGHT_MODE = "DefaultKeyIsNightMode"
        const val KEY_EDIT_THEME_MODE = "DefaultKeyThemeMode"
        const val KEY_EDIT_FILTER_SORTING = "DefaultKeyEditFilterSorting"
        const val KEY_EDIT_FILTER_VIEW = "DefaultKeyEditFilterView"
        const val KEY_EDIT_FILTER_GROUP = "DefaultKeyEditFilterGroup"
    }
}