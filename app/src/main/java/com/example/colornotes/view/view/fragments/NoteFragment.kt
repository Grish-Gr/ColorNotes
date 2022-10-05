package com.example.colornotes.view.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.colornotes.R
import com.example.colornotes.databinding.FragmentNoteBinding
import com.example.colornotes.view.model.ColorGroupData
import com.example.colornotes.view.model.NoteData
import com.example.colornotes.view.viewmodels.NoteViewModel
import kotlinx.coroutines.launch

class NoteFragment: BaseFragment() {

    private lateinit var binding: FragmentNoteBinding
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
        initLiveData()
        viewModel.getListGroup()
        if (arguments != null)
            fillNoteFromBundle(arguments as Bundle)
    }

    private fun initActionView(){
        binding.closeFragmentNoteAdd.setOnClickListener {
            backToParentFragment()
        }
        binding.successNote.setOnClickListener {
            lifecycleScope.launch {
                val currentColorGroup = getCurrentColorGroup()
                if (currentColorGroup == null){
                    showToast("Choose Color")
                    return@launch
                }

                if (viewModel.currentNoteData == null){
                    viewModel.addNote(
                        getTitleNote(),
                        getTextNote(),
                        currentColorGroup.id)
                } else {
                    viewModel.currentNoteData?.titleNote = getTitleNote()
                    viewModel.currentNoteData?.textNote  = getTextNote()
                    viewModel.updateNote(viewModel.currentNoteData!!, currentColorGroup.id)
                }.join()
                backToParentFragment()
            }
        }
    }

    private fun initLiveData(){
        viewModel.listGroup.observe(viewLifecycleOwner){
            initChipGroup(it)
        }
    }

    private fun initChipGroup(listGroup: List<ColorGroupData>){
        listGroup.map { colorGroup ->
            binding.filterGroupChip.addView(getColorGroupChip(colorGroup))
        }
        if (viewModel.currentNoteData?.colorGroup?.id != null)
            binding.filterGroupChip.check(viewModel.currentNoteData?.colorGroup?.id!!.toInt())
    }

    private fun showToast(message: String) =
        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()

    private fun fillNoteFromBundle(bundle: Bundle){
        val note: NoteData = bundle.getParcelable(KEY_PUT_DATA) ?: return
        viewModel.currentNoteData = note
        binding.filterGroupChip.check(viewModel.getIdColorGroup())
        binding.inputTitleNote.editText?.setText(viewModel.currentNoteData?.titleNote)
        binding.inputTextNote.setText(viewModel.currentNoteData?.textNote)
    }

    private fun getCurrentColorGroup(): ColorGroupData? {
        val idChip = binding.filterGroupChip.checkedChipId
        return if (idChip == View.NO_ID){
            null
        } else {
            binding.filterGroupChip[binding.filterGroupChip.checkedChipId]
                .getTag(R.string.tag_chip_color_group) as ColorGroupData
        }
    }
    private fun getTitleNote(): String = binding.inputTitleNote.editText?.text.toString()
    private fun getTextNote(): String = binding.inputTextNote.text.toString()
}