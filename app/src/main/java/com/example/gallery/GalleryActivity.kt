package com.example.gallery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallery.databinding.ActivityGalleryBinding
import com.example.gallery.util.GalleryImage
import com.example.gallery.util.ImageExtractor

class GalleryActivity : AppCompatActivity(), GalleryAdapter.ClickListener {
    private lateinit var binding: ActivityGalleryBinding
    private val isEditable = true
    private lateinit var adapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupDataToRecyclerView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImageExtractor.PERMISSION_CODE) setupDataToRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = GalleryAdapter(this, isEditable)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, COUNT_OF_ROW)
    }

    private fun setupDataToRecyclerView() {
        val images = ImageExtractor(this).listOfImages()
        adapter.addItems(images)
    }

    override fun onItemClick(item: GalleryImage, position: Int) {
        start(this, adapter.getImages(), false)
    }

    companion object {
        const val COUNT_OF_ROW = 3
        fun start(context: Context, imageArray: MutableList<GalleryImage>, isEnabled: Boolean) {
            val intent = Intent(context, GalleryActivity::class.java)
            val image = imageArray as Parcelable
            intent.putExtra("IMAGES", imageArray)
            context.startActivity(intent)
        }
    }


}