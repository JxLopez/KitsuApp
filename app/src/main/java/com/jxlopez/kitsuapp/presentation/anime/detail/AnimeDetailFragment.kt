package com.jxlopez.kitsuapp.presentation.anime.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jxlopez.kitsuapp.R
import com.jxlopez.kitsuapp.databinding.FragmentAnimeDetailBinding
import com.jxlopez.kitsuapp.model.AnimeItem
import com.jxlopez.kitsuapp.presentation.BaseFragment
import com.jxlopez.kitsuapp.utils.extensions.loadImageUrl
import com.jxlopez.kitsuapp.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentAnimeDetailBinding
    private val viewModel: AnimeDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeDetailBinding.inflate(inflater)
        enabledToolbar(binding.toolbar)
        observe(viewModel.getViewState(), ::onViewState)
        return binding.root
    }

    private fun getArgs() {
        arguments?.let {
            val args = AnimeDetailFragmentArgs.fromBundle(it)
            viewModel.setIdAnime(args.idAnime)
            viewModel.getAnimeById()
        } ?: also {
            findNavController().popBackStack()
        }
    }

    private fun onViewState(state: AnimeDetailViewState?) {
        when (state) {
            is AnimeDetailViewState.Anime -> {
                loadData(state.anime)
            }
            is AnimeDetailViewState.Error -> {}
            is AnimeDetailViewState.Company -> {
                binding.tvProductions.text = String.format(getString(R.string.productions_details_fragment), state.name)
            }
            is AnimeDetailViewState.Director -> {
                binding.tvDirector.text = String.format(getString(R.string.director_details_fragment), state.name)
            }
            AnimeDetailViewState.Loading -> {}
            else -> {}
        }
    }

    private fun loadData(anime: AnimeItem) {
        binding.collapsingToolbar.title = anime.title
        binding.tvTitleRoman.text = anime.titles
        binding.tvRating.text = String.format("%.1f", anime.rating)
        binding.tvStartDate.text = anime.releaseDate
        binding.tvDescription.text = anime.description
        binding.backdrop.loadImageUrl(anime.coverImage)
        binding.ivFrontPage.loadImageUrl(anime.posterImage)
    }
}