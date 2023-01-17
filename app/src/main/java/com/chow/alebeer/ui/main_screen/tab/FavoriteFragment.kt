package com.chow.alebeer.ui.main_screen.tab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chow.alebeer.adapter.FavoriteBinder
import com.chow.alebeer.databinding.FragmentFavoriteBinding
import com.chow.alebeer.model.BeerEntity
import com.chow.alebeer.ui.main_screen.MainViewModel
import mva2.adapter.ListSection
import mva2.adapter.MultiViewAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : Fragment() {
    private var binding: FragmentFavoriteBinding? = null
    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            rvFavorites.apply {
                adapter = MultiViewAdapter().apply {
                    registerItemBinders(
                        FavoriteBinder(
                            onClickedButtonDelete = this@FavoriteFragment.viewModel::deleteBeer,
                            onClickedButtonUpdate = this@FavoriteFragment.viewModel::updateBeer
                        )
                    )
                }
                layoutManager = LinearLayoutManager(activity)
            }
            viewModel.getFavorites().observe(viewLifecycleOwner) {
                (rvFavorites.adapter as MultiViewAdapter).apply {
                    removeAllSections()
                    addSection(ListSection<BeerEntity>().apply { addAll(it) })
                    notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}