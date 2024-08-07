package com.example.news.ui.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.MainActivity
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.api.HttpResponse
import com.example.news.viewmodels.NewsViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter:NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        viewModel=(activity as MainActivity).viewModel

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleResponse()
        setUpRecyclerView(requireActivity().application)
        handleNewsAdapterClick()
        binding.favButton.setOnClickListener{
            findNavController().navigate(R.id.homeToSave)
        }
        binding.searchButton.setOnClickListener {
            val searchQuery = binding.search.text.toString()
            if (searchQuery.isNotEmpty()){
                viewModel.searchedNews(searchQuery)
            }
        }
    }

    private fun handleResponse(){
        viewModel.news.observe(viewLifecycleOwner) { response ->
            when (response) {
                is HttpResponse.Success -> {
                    binding.progressBar.visibility=  View.INVISIBLE
                    response.data?.let { news ->
                        newsAdapter.differ.submitList(news.articles)
                    }
                }

                is HttpResponse.Error -> {
                    binding.progressBar.visibility=  View.INVISIBLE
                    response.message?.let {
                        Log.d("Some thing went wrong ...", it)
                    }
                }

                is HttpResponse.Loading -> {
                    binding.progressBar.visibility=  View.VISIBLE
                }
            }
        }
    }
    private fun handleNewsAdapterClick(){
        newsAdapter.setOnItemClickListener {
            if(it.url.isNotEmpty()) {
                val direction = HomeFragmentDirections.homeToNews(it)
                findNavController().navigate(direction)
            }

        }
    }
    private fun setUpRecyclerView(application: Application){
        newsAdapter= NewsAdapter(application)
        binding.newsList.adapter=newsAdapter
        binding.newsList.layoutManager= LinearLayoutManager(application)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}