package com.chow.alebeer.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.chow.alebeer.R
import com.chow.alebeer.databinding.ItemBeerBinding
import com.chow.alebeer.model.BeerModel
import com.chow.alebeer.other.Resources
import com.chow.alebeer.other.invisible
import com.chow.alebeer.other.show
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder

class BeerBinder(
    private val onClickedButtonSave: (Int, BeerModel, Bitmap?) -> Unit
) : ItemBinder<BeerModel, BeerBinder.BeerViewHolder>() {

    inner class BeerViewHolder(val binding: ItemBeerBinding) : ItemViewHolder<BeerModel>(binding.root)

    override fun bindViewHolder(holder: BeerViewHolder, item: BeerModel) {
        holder.binding.apply {
            Glide.with(ivImage.context).load(
                item.localImagePath.ifBlank { item.image }
            ).into(ivImage)
            tvName.text = item.name
            tvPrice.text = item.price
            when {
                item.isSaving -> {
                    btnSave.apply {
                        text = Resources.getString(R.string.saving_button)
                        isFocusable = false
                        isFocusableInTouchMode = false
                        show()
                    }
                    etNote.apply {
                        setText(item.note)
                        isFocusable = false
                        isFocusableInTouchMode = false
                    }
                }
                item.note.isBlank() -> {
                    btnSave.apply {
                        text = Resources.getString(R.string.save_button)
                        setOnClickListener {
                            onClickedButtonSave.invoke(
                                holder.adapterPosition,
                                item.copy(note = etNote.text.toString()),
                                if (ivImage.drawable != null) ivImage.drawable.toBitmap() else null
                            )
                        }
                        show()
                    }
                    vUnderline.show()
                    etNote.apply {
                        setText("")
                        isFocusable = true
                        isFocusableInTouchMode = true
                    }
                }
                item.note.isNotBlank() -> {
                    etNote.apply {
                        setText(item.note)
                        isFocusable = false
                        isFocusableInTouchMode = false
                    }
                    btnSave.invisible()
                    vUnderline.invisible()
                }
            }
        }
    }

    override fun createViewHolder(parent: ViewGroup) = BeerViewHolder(
        ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun canBindData(item: Any): Boolean {
        return item is BeerModel
    }
}