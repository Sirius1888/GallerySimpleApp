package com.example.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gallery.databinding.ItemImageBinding
import com.example.gallery.util.GalleryImage

class GalleryAdapter(private var listener: ClickListener, private var isEditable: Boolean) : RecyclerView.Adapter<ColorViewHolder>() {

    private var items = mutableListOf<GalleryImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val item = items[position]
        with(holder) {
            bind(item)
            if (isEditable) {
                binding.root.setOnClickListener {
                    item.isSelected = !item.isSelected
                    listener.onItemClick(item, position)
                    notifyItemChanged(position)
                }
            }
        }
    }

    fun addItems(data: MutableList<GalleryImage>) {
        items = data
        notifyDataSetChanged()
    }

    fun getImages(): MutableList<GalleryImage> {
        return items
    }

    interface ClickListener {
        fun onItemClick(item: GalleryImage, position: Int)
    }
}

class ColorViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GalleryImage) {
        if (item.isSelected) binding.ivCheck.visibility = View.VISIBLE
        else binding.ivCheck.visibility = View.GONE
        Glide.with(binding.root.context)
            .load(item.path)
            .into(binding.ivImage)
    }
}