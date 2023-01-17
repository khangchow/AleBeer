package com.chow.alebeer.ui.main_screen.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chow.alebeer.adapter.BeerBinder
import com.chow.alebeer.databinding.FragmentBeerBinding
import com.chow.alebeer.model.BeerModel
import com.chow.alebeer.other.Resource
import com.chow.alebeer.ui.main_screen.MainViewModel
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class BeerFragment : Fragment() {
    private var binding: FragmentBeerBinding? = null
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            rvBeers.apply {
                adapter = MultiViewAdapter().apply {
                    registerItemBinders(BeerBinder { index, beer, bitmap ->
                        viewModel.saveBeer(index, beer, bitmap)
                    })
                }
                layoutManager = LinearLayoutManager(activity)
            }
            viewModel.getBeersStatus.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            (rvBeers.adapter as MultiViewAdapter).apply {
                                removeAllSections()
                                addSection(ListSection<BeerModel>().apply { addAll(it) })
                                notifyDataSetChanged()
                            }
                        }
                    }
                    else -> {}
                }
            }
            viewModel.saveBeersStatus.observe(viewLifecycleOwner) { event ->
                if (event.hasBeenHandled.not()) {
                    event.getContentIfNotHandled()?.let { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                resource.data?.let {
                                    (rvBeers.adapter as MultiViewAdapter).apply {
                                        removeAllSections()
                                        addSection(ListSection<BeerModel>().apply { addAll(it) })
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            viewModel.getFavorites().observe(viewLifecycleOwner) {
                viewModel.onBeerUpdated()
            }
        }
        viewModel.getBeers()
    }

    companion object {
        fun newInstance() = BeerFragment()
    }
}