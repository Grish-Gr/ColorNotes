package com.example.colornotes.view.view.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        initRecyclerView()
        initActionView()
        initLiveData()
    }

    private fun initRecyclerView(){
        val adapter = MainAdapter()
        adapter.setAction({
            openFragment(AddNoteFragment(), it)
        }, {
            false
        })
        binding.listNotes.adapter = adapter
        binding.listNotes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun initLiveData(){
        viewModel.listDataNote.observe(viewLifecycleOwner){
            (binding.listNotes.adapter as MainAdapter).setListNoteData(it)
            binding.progressDownloadNotes.visibility = View.INVISIBLE
        }
        viewModel.getListNote()
    }

    private fun initActionView(){
        binding.mainToolBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.item_menu_filter_notes -> {
                    showBottomFragment()
                    true
                }
                else -> false
            }
        }
        binding.addNote.setOnClickListener {
            openFragment(AddNoteFragment())
        }
    }

    private fun showBottomFragment() {
        val filterFragment = FilterFragment()
        filterFragment.show(parentFragmentManager, FilterFragment.TAG_FILTER_FRAGMENT)
    }
}