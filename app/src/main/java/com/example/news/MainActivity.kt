package com.example.news

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.news.database.NewsDatabase
import com.example.news.databinding.ActivityMainBinding
import com.example.news.repository.NewsRepository

import com.example.news.viewmodels.NewsViewModel
import com.example.news.viewmodels.NewsViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var viewModel:NewsViewModel
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewmodel setup
        val newsDao =NewsDatabase.getDatabase(this).newsDao()
        val repository = NewsRepository(newsDao)
        val viewModelFactory = NewsViewModelFactory(repository)
        viewModel= ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}