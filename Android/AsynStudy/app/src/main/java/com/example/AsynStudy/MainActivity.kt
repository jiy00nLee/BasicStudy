package com.example.AsynStudy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.*
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.AsynStudy.databinding.ActivityMainBinding
import com.example.AsynStudy.databinding.FragmentStudyBinding
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewstart(savedInstanceState)
        databinding(savedInstanceState)
        viewfinal(savedInstanceState)
    }

    private fun viewstart(savedInstanceState: Bundle?){
    }

    private fun databinding(savedInstanceState: Bundle?){

    }
    private fun viewfinal(savedInstanceState: Bundle?){

    }

// #Custom Functions ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private fun whenUsingCoroutinePermissions(){

/*   private suspend fun getCurrentLocation(){
        val yourActivity = requireActivity() as AppCompatActivity

        //Request permission
        val result = PermissionCoroutine.request(yourActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)

        when{
            result.isGranted() -> {
                // Permission granted
            }

            result.isNeverAskAgain() ->{
                // User disabled the permission permanently, show app settings page if you want
                withContext(Dispatchers.Main){
                    PermissionCoroutine.showPermissionSettings(yourActivity)
                }
            }

            result.isTemporaryDenied() ->{
                // User denied the permission one time
            }

            result.isRefused() ->{
                // Permission refused either on time or permanently disabled
            }

        }

    }*/

/*   private suspend fun checkPermission(){
       viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
           val resultData = PermissionManager.requestPermissions(
               this@Fragment, RESULT_CODE,
               Manifest.permission.CAMERA)
       }
   }*/

}


}
