package com.example.colornotes.view.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentMainBinding
import com.example.colornotes.view.adapters.MainAdapter
import com.example.colornotes.view.adapters.TypeHolder
import com.example.colornotes.view.adapters.TypeHolder.*
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.view.filter.FilterSetting
import com.example.colornotes.view.viewmodels.MainViewModel

class MainFragment: BaseFragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.filterSetting = getSaveFilterSetting()
    }

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
        initActionView()
        initRecyclerView()
        initLayoutManager()
        initLiveData()
        Log.e("TAG", "onViewCreated")
        viewModel.getListNote()
    }

    private fun initRecyclerView(){
        val adapter = MainAdapter(viewModel.getFilterView())
        adapter.setAction({
            openFragment(NoteFragment(), it)
        }, { view, note ->
            showPopupMenu(view, note)
            true
        })
        binding.listNotes.adapter = adapter
    }

    private fun initLayoutManager(){
        binding.listNotes.layoutManager = when (viewModel.getFilterView()){
            TYPE_ITEM_LINE, TYPE_ITEM_ALL_LINE -> LinearLayoutManager(
                this.context, RecyclerView.VERTICAL, false)
            TYPE_ITEM_GRID -> StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        }
    }

    private fun initViewHolders(){
        (binding.listNotes.adapter as MainAdapter).setTypeItem(viewModel.getFilterView())
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
                    showBottomFilerFragment()
                    true
                }
                else -> false
            }
        }
        binding.addNote.setOnClickListener {
            openFragment(NoteFragment())
        }
    }

    private fun showBottomFilerFragment() {
        openBottomFragment(FilterFragment(), viewModel.filterSetting) { _, bundle ->
            val filterSetting: FilterSetting? =
                bundle.getParcelable(FilterFragment.KEY_FILTER_FRAGMENT)
            viewModel.filterSetting = filterSetting ?: FilterSetting.getDefaultFilterSetting()
            initLayoutManager()
            initViewHolders()
        }
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
        saveFilterSetting(viewModel.filterSetting)
    }
}