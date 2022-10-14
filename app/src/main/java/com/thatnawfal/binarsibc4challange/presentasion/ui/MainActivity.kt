package com.thatnawfal.binarsibc4challange.presentasion.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.thatnawfal.binarsibc4challange.wrapper.Resource
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.data.local.database.entity.NotesEntity
import com.thatnawfal.binarsibc4challange.databinding.ActivityMainBinding

import com.thatnawfal.binarsibc4challange.di.ServiceLocator
import com.thatnawfal.binarsibc4challange.presentasion.ui.adapter.NotesAdapter
import com.thatnawfal.binarsibc4challange.presentasion.ui.adapter.itemClickListerner
import com.thatnawfal.binarsibc4challange.presentasion.ui.editnoteform.EditNoteBottomSheet
import com.thatnawfal.binarsibc4challange.presentasion.ui.editnoteform.OnChangeListnerEdited
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.CreateNoteBottomSheet
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.OnChangeListenerCreate
import com.thatnawfal.binarsibc4challange.utills.viewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NotesViewModel by viewModelFactory {
        NotesViewModel(ServiceLocator.provideLocalRepository(this))
    }

    private val adapter: NotesAdapter by lazy {
        NotesAdapter(object : itemClickListerner {
            override fun onItemClicked(note: NotesEntity) {
                ActionDialog(object : buttonClickListener {
                    override fun actionEdit() {
                        viewModel.getNotesById(note.id)
                    }

                    override fun actionDelete() {
                        viewModel.deleteNotes(note)
                        setData()
                    }

                }).show(supportFragmentManager, "action dialog")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        viewClickListener()
        observeAction()

        showEditNoteForm()

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
                    it.payload?.let { list -> adapter.setData(list) }
                    initList()
                }
                is Resource.Error -> TODO()
            }
        }

        viewModel.deleteResult.observe(this) {
            when (it) {
                is Resource.Error ->
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                is Resource.Loading ->
                    Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
                is Resource.Success ->
                    Toast.makeText(applicationContext, "Notes Deleted", Toast.LENGTH_LONG).show()
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
            CreateNoteBottomSheet(object : OnChangeListenerCreate {
                override fun onNoteCreated() {
                    setData()
                }
            }).show(supportFragmentManager, CreateNoteBottomSheet::class.java.simpleName)
        }
    }

    private fun showEditNoteForm(){
        viewModel.notesDetailResult.observe(this){
            when (it) {
                is Resource.Error -> TODO()
                is Resource.Loading -> {
                    Toast.makeText(applicationContext, "Loading", Toast.LENGTH_LONG).show()
                }
                is Resource.Success -> {
                    val currentDialog =
                        supportFragmentManager.findFragmentByTag(EditNoteBottomSheet::class.java.simpleName)
                    if (currentDialog == null) {
                        it.payload?.let { note ->
                            EditNoteBottomSheet(note, object : OnChangeListnerEdited {
                                override fun onNoteEdited() {
                                    setData()
                                }
                            }).show(supportFragmentManager, CreateNoteBottomSheet::class.java.simpleName)
                        }
                    }
                }
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_action_logout -> {
                viewModel.setIdPreference(0)
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
                true
            }
            else -> {super.onOptionsItemSelected(item)}
        }
    }
}