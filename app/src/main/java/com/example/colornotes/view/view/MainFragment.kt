package com.example.colornotes.view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentMainBinding
import com.example.colornotes.view.adapters.MainAdapter
import com.example.colornotes.view.viewmodels.MainViewModel

class MainFragment: BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView(){
        val adapter = MainAdapter()
    }

    private fun initLiveData(){
        viewModel.listDataNote.observe(viewLifecycleOwner){
            (binding.listNotes.adapter as MainAdapter).setListNoteData(it)
        }
        viewModel.listColorGroup.observe(viewLifecycleOwner){

        }
        viewModel.getListNote()
        viewModel.getListColorGroup()
    }

    private fun initToolBar(){
        binding.mainToolBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.item_menu_filter_notes -> {
                    showBottomFragment()
                    true
                }

                else -> false
            }
        }
    }

    private fun showBottomFragment() {
        val filterFragment = FilterFragment()
        filterFragment.show(parentFragmentManager, FilterFragment.TAG_FILTER_FRAGMENT)
    }
}