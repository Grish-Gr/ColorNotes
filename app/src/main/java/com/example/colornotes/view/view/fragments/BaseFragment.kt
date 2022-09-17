package com.example.colornotes.view.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.colornotes.R
import com.example.colornotes.view.view.filter.FilterSetting

open class BaseFragment: Fragment() {

    protected fun openFragment(fragment: Fragment, parcelable: Parcelable? = null){
        if (parcelable != null){
            Log.e("TAG", "Put parcelable")
            val bundle = Bundle()
            bundle.putParcelable(TAG_PUT_DATA, parcelable)
            fragment.arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .addToBackStack(TAG_DEFAULT_BACK_STACK)
            .replace(R.id.main_container_fragment, fragment)
            .commit()
    }

    protected fun backToParentFragment(){
        parentFragmentManager.popBackStack()
    }

    @SuppressLint("CommitPrefEdits")
    protected fun getSaveFilterSetting(): FilterSetting{
        val sharedPreference = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val filterSorting = sharedPreference?.getInt(KEY_EDIT_FILTER_SORTING, 0) ?: 0
        val filterView = sharedPreference?.getInt(KEY_EDIT_FILTER_VIEW, 0) ?: 0
        val saveGroup = sharedPreference?.getLong(KEY_EDIT_FILTER_GROUP, -1L)
        val filterGroup = if (saveGroup == -1L) null
                          else saveGroup
        return FilterSetting(filterSorting, filterView, filterGroup)
    }

    @SuppressLint("CommitPrefEdits")
    protected fun saveFilterSetting(filterSetting: FilterSetting){
        val sharedPreference = activity?.getSharedPreferences(NAME_SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val edit = sharedPreference?.edit()
        with(filterSetting){
            edit?.putInt(KEY_EDIT_FILTER_SORTING, filterSorting)
            edit?.putInt(KEY_EDIT_FILTER_VIEW, filterView)
            if (filterGroup == null) return
            edit?.putLong(KEY_EDIT_FILTER_GROUP, filterGroup)
        }
    }

    companion object{
        const val TAG_PUT_DATA = "DefaultTagPutData"
        const val TAG_DEFAULT_BACK_STACK = "DefaultTagBackStack"
        const val KEY_RESULT_FRAGMENT = "DefaultKeyResultFragment"
        const val NAME_SHARED_PREFERENCE = "DefaultNameSharedPreference"
        const val KEY_EDIT_FILTER_SORTING = "DefaultKeyEditFilterSorting"
        const val KEY_EDIT_FILTER_VIEW = "DefaultKeyEditFilterView"
        const val KEY_EDIT_FILTER_GROUP = "DefaultKeyEditFilterGroup"
    }
}