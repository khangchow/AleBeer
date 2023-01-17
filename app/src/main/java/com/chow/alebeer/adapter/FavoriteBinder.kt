package com.chow.alebeer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.chow.alebeer.databinding.ItemFavoriteBinding
import com.chow.alebeer.model.BeerEntity
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class FavoriteBinder(
    private val onClickedButtonDelete: (BeerEntity) -> Unit,
    private val onClickedButtonUpdate: (BeerEntity) -> Unit
) : ItemBinder<BeerEntity, FavoriteBinder.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        ItemViewHolder<BeerEntity>(binding.root)

    override fun bindViewHolder(holder: FavoriteViewHolder, item: BeerEntity) {
        holder.binding.apply {
            Glide.with(ivImage.context).load(item.localImagePath).into(ivImage)
            tvName.text = item.name
            tvPrice.text = item.price
            etNote.setText(item.note)
            btnDelete.setOnClickListener { item.run(onClickedButtonDelete) }
            btnUpdate.setOnClickListener {
                item.copy(note = etNote.text.toString()).run(onClickedButtonUpdate)
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup) = FavoriteViewHolder(
        ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun canBindData(item: Any): Boolean {
        return item is BeerEntity
    }
}