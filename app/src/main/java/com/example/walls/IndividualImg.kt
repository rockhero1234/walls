package com.example.walls

import android.R.attr
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.io.FileOutputStream
import android.os.Environment
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


@Suppress("DEPRECATION")
class IndividualImg : AppCompatActivity() {
    lateinit var Image_Imageview: ImageView
    lateinit var Back_Imagebtn: ImageButton
    lateinit var Download_Imagebtn: ImageButton
    lateinit var Set_Imagebtn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image)
        Image_Imageview = findViewById(R.id.image)
        Back_Imagebtn = findViewById(R.id.back_btn)
        Download_Imagebtn = findViewById(R.id.download_btn)
        Set_Imagebtn = findViewById(R.id.set_btn)

        val intent = intent
        val url = intent.getStringExtra("url")
        val id = intent.getStringExtra("id")
        Glide.with(this).load(url).placeholder(R.drawable.background_color).into(Image_Imageview)

        Back_Imagebtn.setOnClickListener {
            onBackPressed()
        }

        Download_Imagebtn.setOnClickListener {
            downloadimage(url.toString(),id.toString())
        }
        Set_Imagebtn.setOnClickListener {

        }
    }

private fun downloadimage(imgurl: String, id: String) {
    try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()

        val input = connection.inputStream

        val directory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "walls")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val outputFile = File(directory, imageName)

        val output = FileOutputStream(outputFile)

        val data = ByteArray(4096)
        var count: Int
        while (input.read(data).also { count = it } != -1) {
            output.write(data, 0, count)
        }

        output.flush()
        output.close()
        input.close()

        // Notify the user that the download was successful.
        Toast.makeText(context, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle any errors that may occur during the download process.
        Toast.makeText(context, "Image download failed", Toast.LENGTH_SHORT).show()
    }
}

    
    private fun verifyPerimssion():Boolean{
        val permissionexternalmemory = ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permissionexternalmemory != PackageManager.PERMISSION_GRANTED) {
            val STORAGE_PERMISSION =
                arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, STORAGE_PERMISSION, 1)
            return false
        }
        return true
    }

}
