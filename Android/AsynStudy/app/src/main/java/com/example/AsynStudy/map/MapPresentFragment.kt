package com.example.AsynStudy.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.AsynStudy.R
import com.example.AsynStudy.databinding.FragmentMapBinding
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MapPresentFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root }

    private var fm : FragmentManager? = null
    private var mapFragment : MapFragment? = null
    private var naverMap: NaverMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewstart(savedInstanceState)
        databinding(savedInstanceState)
        viewfinal(savedInstanceState)
    }


    private fun viewstart(savedInstanceState: Bundle?) {
        makeMapView()
    }

    private val onMapReadyListener = OnMapReadyCallback { nMap ->
        this.naverMap = nMap.apply {  }
    }

    override fun onMapReady(nMap: NaverMap) {
        //카메라 움직임 중 변경이벤트 콜백 메서드
        nMap.addOnCameraChangeListener { int, boolean ->

        }
        //카메라 움직임 후 대기이벤트 콜백 메서드
        nMap.addOnCameraIdleListener {

        }
        //기기의 위치가 변경될 경우 호출되는 콜백 메서드
        nMap.addOnLocationChangeListener {   }
    }

    private fun databinding(savedInstanceState: Bundle?) {

    }

    private fun viewfinal(savedInstanceState: Bundle?) {

    }

    // #Custom Functions ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private fun makeMapView() {
        fm = childFragmentManager
        mapFragment = fm?.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm!!.beginTransaction().add(R.id.map, it).commit()
            }
        /*mapFragment?.let{ it.getMapAsync {} }*/
        /*mapFragment?.getMapAsync { nMap ->
            this.naverMap = nMap

        }*/
        mapFragment?.getMapAsync(this)
        //mapFragment?.getMapAsync(onMapReadyListener)
    }



}