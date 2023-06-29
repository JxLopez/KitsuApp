package com.jxlopez.kitsuapp.presentation.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jxlopez.kitsuapp.R
import com.jxlopez.kitsuapp.databinding.FragmentAnimeListBinding
import com.jxlopez.kitsuapp.presentation.BaseFragment
import com.jxlopez.kitsuapp.utils.Constants
import com.jxlopez.kitsuapp.utils.extensions.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeListFragment : BaseFragment() {
    private lateinit var binding: FragmentAnimeListBinding
    private val animeAdapter by lazy {
        AnimeAdapter()
    }
    private val viewModel: AnimeViewModel by viewModels()
    private var isSend = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(inflater)
        configureBar()
        configureRecyclers()
        setScroll()
        setListeners()
        observe(viewModel.getViewState(), ::onViewState)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_search, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.length?: 0 > 2) {
                    viewModel.searchAnimes(newText?.trim() ?: "")
                } else if(newText?.isEmpty() == true) {
                    viewModel.getAnimes()
                }
                return true
            }
        })
        searchView.setOnClickListener {view ->  }
    }


    private fun configureBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.welcome_anime_list_fragment)
        setHasOptionsMenu(true)
    }

    private fun configureRecyclers() {
        binding.rvAnimes.layoutManager = GridLayoutManager(requireActivity(), Constants.COUNT_SPAN)
        binding.rvAnimes.adapter = animeAdapter
    }

    private fun setScroll() {
        binding.nestedScrollView.setOnScrollChangeListener {
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int,
            ->
            if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v.measuredHeight)) {
                if (viewModel.searching.value == false) {
                    if (!isSend) {
                        val offset = viewModel.offset.value ?: 1
                        val maxOffset = viewModel.maxOffset.value ?: 0
                        if (offset < maxOffset) {
                            isSend = true
                            viewModel.getAnimesPaginatedUseCase()
                        }
                    }
                }
            }
        }
    }

    private fun setListeners() {
        animeAdapter.setOnItemClickListener { anime ->
            findNavController().navigate(
                AnimeListFragmentDirections.actionAnimeListFragmentToAnimeDetailFragment(
                    anime.id
                )
            )
        }
    }

    private fun onViewState(state: AnimeViewState?) {
        when (state) {
            is AnimeViewState.AnimeList -> {
                hideProgressBar()
                if (state.animes.isNotEmpty()) {
                    binding.tvError.visibility = View.GONE
                    animeAdapter.submitList(state.animes)
                } else {
                    binding.tvError.text =
                        getString(R.string.not_found_anime_list_fragment)
                    binding.tvError.visibility = View.VISIBLE
                }
                isSend = false
            }
            is AnimeViewState.AnimeListPaginated -> {
                hideProgressBar()
                isSend = false
                if (state.animes.isNotEmpty()) {
                    binding.tvError.visibility = View.GONE
                    animeAdapter.submitList(state.animes)
                } else {
                    binding.tvError.text =
                        getString(R.string.not_found_anime_list_fragment)
                    binding.tvError.visibility = View.VISIBLE
                }
            }
            is AnimeViewState.Error -> {
                hideProgressBar()
                binding.tvError.text = String.format(getString(R.string.not_found_anime_list_fragment), state.error)
                binding.tvError.visibility = View.VISIBLE
            }
            AnimeViewState.Loading -> showProgressBar()
            else -> {}
        }
    }

    private fun hideProgressBar() {
        binding.progressLoading.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressLoading.visibility = View.VISIBLE
    }
}