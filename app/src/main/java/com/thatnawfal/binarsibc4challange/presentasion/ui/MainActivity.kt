package com.thatnawfal.binarsibc4challange.presentasion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.mypassword.wrapper.Resource
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.ActivityMainBinding
import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.presentasion.ui.adapter.NotesAdapter
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.CreateNoteBottomSheet
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.CreateNoteViewModel
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.OnIdUserChangedListener
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NotesViewModel by viewModelFactory {
        NotesViewModel(ServiceLocator.provideLocalRepository(this))
    }

    private val adapter = NotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewClickListener()
        setData()
        observeAction()
        initList()

    }

    private fun initList() {
        binding.rvMainNotesList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvMainNotesList.adapter = adapter
    }

    private fun observeAction() {
        viewModel.notesListResult.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    setLoadingState(true)
                }
                is Resource.Success -> {
                    setLoadingState(false)
                    it.payload?.let { list -> adapter.submitData(list) }
                }
                is Resource.Error -> TODO()
            }
        }
    }

    private fun setLoadingState(b: Boolean){
        binding.pbMainLoadList.isVisible = b
        binding.rvMainNotesList.isVisible = !b
    }

    private fun setData(){
        viewModel.getNotesList(viewModel.getIdPreference().toString().toInt())
    }

    private fun viewClickListener() {
        binding.fbMainAdd.setOnClickListener { showCreateNoteForm() }
    }

    private fun showCreateNoteForm(
    ) {
        val currentDialog =
            supportFragmentManager.findFragmentByTag(CreateNoteBottomSheet::class.java.simpleName)
        if (currentDialog == null) {
            CreateNoteBottomSheet().apply {
//                null
            }.show(supportFragmentManager, CreateNoteBottomSheet::class.java.simpleName)
        }
    }
}