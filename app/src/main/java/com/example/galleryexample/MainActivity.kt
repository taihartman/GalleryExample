package com.example.galleryexample

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.galleryexample.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            var data: Intent? = result.data
            if(data!=null){
                //get the image
                 try {
                     val selectedImage = data.data
                     val bmp = getBitmapFromUri(selectedImage)
                    binding.imageView1.setImageBitmap(bmp)
                 }   catch (e: java.lang.Exception){
                     e.printStackTrace()
                     Toast.makeText(this,"Please try again",Toast.LENGTH_LONG).show()
                 }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }
    }

    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri?): Bitmap{
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri!!,"r")
        val fileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }//getBitmapFromUri


}//MainActivity