package com.thatnawfal.binarsibc4challange.presentasion.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.thatnawfal.binarsibc4challange.R
import com.thatnawfal.binarsibc4challange.databinding.ActivityMainBinding
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.CreateNoteBottomSheet
import com.thatnawfal.binarsibc4challange.presentasion.ui.noteform.OnIdUserChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewClickListener()
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