package com.example.news.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.news.MainActivity
import com.example.news.R
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.models.Article
import com.example.news.viewmodels.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {
    private lateinit var binding:FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private  var isFavorite:Boolean=false
    val args:NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNewsBinding.inflate(inflater,container,false)
        viewModel= (activity as MainActivity).viewModel

        viewModel.isNewsPresent(args.article.url).observe(viewLifecycleOwner, Observer {
            isFavorite=it

            if(isFavorite){
                binding.saveButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#eb4034"))
            }
            else{
                binding.saveButton.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val article=args.article
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
        handleBackButton()
        handleSaveButton(article)
    }

    private fun handleSaveButton(article: Article) {
        binding.saveButton.setOnClickListener{
            if (isFavorite){
                viewModel.deleteNews(article)
                Toast.makeText(
                    requireActivity().application,
                    "News Removed from the Favorites",
                    Toast.LENGTH_SHORT
                ).show()

            }
            else{
                viewModel.addNews(article)
                Toast.makeText(
                    requireActivity().application,
                    "Your news is Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun handleBackButton() {
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.newsToHome)
        }
    }

}