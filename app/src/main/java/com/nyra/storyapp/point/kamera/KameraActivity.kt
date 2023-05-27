package com.nyra.storyapp.point.kamera

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.OutputFileResults
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.nyra.storyapp.R.string
import com.nyra.storyapp.databinding.ActivityKameraBinding
import com.nyra.storyapp.utils.ValConst.KEY_CAMERA_BACK
import com.nyra.storyapp.utils.ValConst.KEY_PICTURE
import com.nyra.storyapp.utils.ValConst.RESULT_CAMERA_X
import com.nyra.storyapp.utils.createFile
import com.nyra.storyapp.utils.ext.showToast
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KameraActivity : AppCompatActivity() {
    private var _activityKameraBinding: ActivityKameraBinding? = null
    private val binding get() = _activityKameraBinding!!

    private lateinit var executorKamera: ExecutorService
    private var selectorKamera: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var captureImage: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityKameraBinding = ActivityKameraBinding.inflate(layoutInflater)
        setContentView(_activityKameraBinding?.root)

        executorInit()
        actionInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        executorKamera.shutdown()
    }

    override fun onResume() {
        super.onResume()
        startKamera()
    }

    private fun executorInit() {
        executorKamera = Executors.newSingleThreadExecutor()
    }

    private fun actionInit() {
        binding.apply {
            captureImg.setOnClickListener {
                takePhoto()
            }
            switchCamera.setOnClickListener {
                selectorKamera = if (selectorKamera.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
                else CameraSelector.DEFAULT_BACK_CAMERA
                startKamera()
            }
        }
    }

    private fun startKamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            captureImage = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    selectorKamera,
                    preview,
                    captureImage
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Timber.e(exc.message)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = captureImage ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    showToast(getString(string.failed_picture))
                    Timber.e(exc.message.toString())
                }

                override fun onImageSaved(output: OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra(KEY_PICTURE, photoFile)
                    intent.putExtra(KEY_CAMERA_BACK, selectorKamera == CameraSelector.DEFAULT_BACK_CAMERA)
                    setResult(RESULT_CAMERA_X, intent)
                    finish()
                }
            }
        )
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}