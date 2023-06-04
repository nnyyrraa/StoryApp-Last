package com.nyra.storyapp.point.story.add

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.Manifest
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.nyra.storyapp.R.string
import com.nyra.storyapp.data.remot.ApiResponse
import com.nyra.storyapp.databinding.ActivityStoryAddBinding
import com.nyra.storyapp.point.kamera.KameraActivity
import com.nyra.storyapp.point.story.StoryViewModel
import com.nyra.storyapp.utils.ManagerSession
import com.nyra.storyapp.utils.ValConst.KEY_PICTURE
import com.nyra.storyapp.utils.ValConst.PERMISSIONS_CODE_REQUEST
import com.nyra.storyapp.utils.ValConst.RESULT_CAMERA_X
import com.nyra.storyapp.utils.ext.gone
import com.nyra.storyapp.utils.ext.show
import com.nyra.storyapp.utils.ext.showOkDialog
import com.nyra.storyapp.utils.ext.showToast
import com.nyra.storyapp.utils.reduceFileImage
import com.nyra.storyapp.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class StoryAddActivity : AppCompatActivity() {
    private val viewModelStory: StoryViewModel by viewModels()

    private var _activityStoryAddBinding: ActivityStoryAddBinding? = null
    private val binding get() = _activityStoryAddBinding!!

    private var fileUpload: File? = null
    private var token: String? = null

    private lateinit var pref: ManagerSession

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, StoryAddActivity::class.java)
            context.startActivity(intent)
        }
        private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityStoryAddBinding = ActivityStoryAddBinding.inflate(layoutInflater)
        setContentView(_activityStoryAddBinding?.root)

        pref = ManagerSession(this)
        token = pref.getToken

        if (!permissionAllGranted()) {
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_REQUIRED,
                PERMISSIONS_CODE_REQUEST
            )
        }

        uiInit()
        actionInit()
    }

    private fun permissionAllGranted() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!permissionAllGranted()) {
            showToast(getString(string.not_permissions))
        }
    }

    private fun uiInit() {
        title = getString(string.new_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun actionInit() {
        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, KameraActivity::class.java)
            launchIntentCameraX.launch(intent)
        }
        binding.btnGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"
            val chooser = Intent.createChooser(intent, getString(string.choose_picture))
            launchIntentGallery.launch(chooser)
        }
        binding.btnUnggah.setOnClickListener {
            imageUpload()
        }
    }

    private val launchIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectImg: Uri = result.data?.data as Uri
            val fileImage = uriToFile(selectImg, this)

            fileUpload = fileImage
            binding.previewImg.setImageURI(selectImg)
        }
    }

    private val launchIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_CAMERA_X) {
            val filePicture = it?.data?.getSerializableExtra(KEY_PICTURE) as File

            fileUpload = filePicture

            val result = BitmapFactory.decodeFile(filePicture.path)

            binding.previewImg.setImageBitmap(result)
        }
    }

    private fun imageUpload() {
        if (fileUpload != null) {
            val fileImg = reduceFileImage(fileUpload as File)
            val desc = binding.editDescStory.text
            if (desc.isBlank()) {
                binding.editDescStory.requestFocus()
                binding.editDescStory.error = getString(string.error_empty_desc)
            } else {
                val typedMediaDesc = desc.toString().toRequestBody("text/plain".toMediaType())
                val imgFileRequest = fileImg.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val multipartImg = MultipartBody.Part.createFormData(
                    "photo",
                    fileImg.name,
                    imgFileRequest
                )
                viewModelStory.addNewStory(multipartImg, typedMediaDesc).observe(this) { response ->
                    when (response) {
                        is ApiResponse.Loading -> {
                            showLoading(true)
                        }
                        is ApiResponse.Success -> {
                            showLoading(false)
                            showToast(getString(string.success_upload))
                            finish()
                        }
                        is ApiResponse.Error -> {
                            showLoading(false)
                            showOkDialog(getString(string.info_upload), response.errorMessage)
                        }
                        else -> {
                            showLoading(false)
                            showToast(getString(string.message_unknown_state))

                        }
                    }
                }
            }
        } else {
            showOkDialog(getString(string.message), getString(string.image_pick))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            btnUnggah.isClickable = !isLoading
            btnCamera.isClickable = !isLoading
            btnGallery.isClickable = !isLoading

            btnUnggah.isEnabled = !isLoading
            btnCamera.isEnabled = !isLoading
            btnGallery.isEnabled = !isLoading
        }
        if (isLoading) binding.progressBar.show() else binding.progressBar.gone()
        if (isLoading) binding.backgroundDim.show() else binding.backgroundDim.gone()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}