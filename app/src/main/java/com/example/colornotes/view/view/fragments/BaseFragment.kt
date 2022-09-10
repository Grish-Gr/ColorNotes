package com.example.colornotes.view.view.fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.colornotes.R

open class BaseFragment: Fragment() {

    protected fun openFragment(fragment: Fragment, parcelable: Parcelable? = null){
        if (parcelable != null){
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
        parentFragmentManager.popBackStack(
            TAG_DEFAULT_BACK_STACK,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object{
        const val TAG_PUT_DATA = "DefaultTagPutData"
        const val TAG_DEFAULT_BACK_STACK = "DefaultTAgBackStack"
    }
}