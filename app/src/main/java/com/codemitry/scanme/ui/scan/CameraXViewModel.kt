package com.codemitry.scanme.ui.scan

import android.app.Application
import android.util.Log
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.ExecutionException

// class by: https://proandroiddev.com/building-barcode-qr-code-scanner-for-android-using-google-ml-kit-and-camerax-220b2852589e
class CameraXViewModel(application: Application) : AndroidViewModel(application) {
    private var cameraProviderLiveData: MutableLiveData<ProcessCameraProvider>? = null

    // Handle any errors (including cancellation) here.
    val processCameraProvider: LiveData<ProcessCameraProvider>
        get() {
            if (cameraProviderLiveData == null) {
                cameraProviderLiveData = MutableLiveData()
                val cameraProviderFuture =
                        ProcessCameraProvider.getInstance(getApplication())
                cameraProviderFuture.addListener(
                        Runnable {
                            try {
                                cameraProviderLiveData!!.setValue(cameraProviderFuture.get())
                            } catch (e: ExecutionException) {
                                // Handle any errors (including cancellation) here.
                                Log.e(TAG, "Unhandled exception", e)
                            } catch (e: InterruptedException) {
                                Log.e(TAG, "Unhandled exception", e)
                            }
                        },
                        ContextCompat.getMainExecutor(getApplication())
                )
            }
            return cameraProviderLiveData!!
        }

    companion object {
        private const val TAG = "CameraXViewModel"
    }
}