package com.example.gallery.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat


data class GalleryImage(
    var path: String,
    var isSelected: Boolean = false
)

class ImageExtractor(private val activity: Activity) {

    fun isHasImagesPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(activity,  Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_CODE)
            return false
        }
        return true
    }

    fun listOfImages(): MutableList<GalleryImage> {
        if (!isHasImagesPermission()) return mutableListOf()
        val cursor : Cursor
        val column_index_data : Int
        val listOfAllImages : ArrayList<GalleryImage> = ArrayList()
        var absolutePathOfImage : String
        val uri : Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy : String = MediaStore.Images.Media.DATE_TAKEN
        cursor = activity.applicationContext.contentResolver.query(uri, projection, null, null, orderBy + " DESC")!!
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursor.moveToNext()){
            absolutePathOfImage = cursor.getString(column_index_data)
            listOfAllImages.add(GalleryImage(absolutePathOfImage, false))
        }

        return listOfAllImages
    }

    companion object {
        val IMAGE_PICK_CODE = 1000;
        val PERMISSION_CODE = 1001;
    }

}