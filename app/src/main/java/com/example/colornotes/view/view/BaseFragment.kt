package com.example.colornotes.view.view

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.colornotes.R

open class BaseFragment: Fragment() {

    protected fun openFragment(fragment: Fragment, parcelable: Parcelable? = null){
        if (parcelable != null){
            val bundle = Bundle()
            bundle.putParcelable(DEFAULT_TAG, parcelable)
            fragment.arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object{
        const val DEFAULT_TAG = "Default Tag"
    }
}