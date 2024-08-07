package com.example.news.ui.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.MainActivity
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.FragmentSavedBinding
import com.example.news.viewmodels.NewsViewModel


class SavedFragment : Fragment(R.layout.fragment_saved) {
    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel=(activity as MainActivity).viewModel
        _binding=FragmentSavedBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView(requireActivity().application)
        handleDatabaseResponse()
        handleAdapterClick()
        handleSwipeDelete()
    }
    private fun handleDatabaseResponse() {
        viewModel.savedNews().observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                newsAdapter.differ.submitList(list)
            }
        })
    }
    private fun handleAdapterClick() {
        newsAdapter.setOnItemClickListener {
            val direction = SavedFragmentDirections.saveToNews(it)
            findNavController().navigate(direction)
        }
    }
    private fun setRecyclerView(application: Application){
        newsAdapter= NewsAdapter(application)
        binding.savedNews.adapter=newsAdapter
        binding.savedNews.layoutManager= LinearLayoutManager(application)
    }
    private fun handleSwipeDelete(){
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteNews(article)
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNews)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}