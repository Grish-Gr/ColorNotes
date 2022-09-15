package com.example.colornotes.view.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentMainBinding
import com.example.colornotes.view.adapters.MainAdapter
import com.example.colornotes.view.model.NoteData
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
        Log.e("TAG", "onViewCreated")
        viewModel.getListNote()
    }

    private fun checkView(string: String) {
        if (binding.listNotes.layoutManager == null) return
        val manager = binding.listNotes.layoutManager
        Log.e("TAG", string)
        Log.e("TAG", "Manager: $manager; Count: ${manager?.childCount}")
    }

    private fun initRecyclerView(){
        val adapter = MainAdapter()
        adapter.setAction({
            openFragment(NoteFragment(), it)
        }, { view, note ->
            showPopupMenu(view, note)
            true
        })
        binding.listNotes.adapter = adapter
        //binding.listNotes.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.listNotes.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    private fun initLiveData(){
        viewModel.listDataNote.observe(this.viewLifecycleOwner){
            Log.e("TAG", it.toString())
            (binding.listNotes.adapter as MainAdapter).setListNoteData(it)
            binding.progressDownloadNotes.visibility = View.INVISIBLE
        }
    }

    private fun initActionView(){
        Log.e("TAG", "Init Action")
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
            openFragment(NoteFragment())
        }
    }

    private fun showBottomFragment() {
        val filterFragment = FilterFragment()
        filterFragment.show(parentFragmentManager, FilterFragment.TAG_FILTER_FRAGMENT)
    }

    private fun showPopupMenu(view: View, noteData: NoteData){
        val popupMenu = PopupMenu(this.context, view)
        popupMenu.inflate(R.menu.item_popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_item_delete_item -> {
                    viewModel.deleteNote(noteData)
                    (binding.listNotes.adapter as MainAdapter).deleteNoteData(noteData)
                    true
                }
                R.id.menu_item_open_item -> {
                    openFragment(NoteFragment(), noteData)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.listDataNote.removeObservers(this.viewLifecycleOwner)
    }
}