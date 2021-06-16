package com.example.AsynStudy.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.AsynStudy.R
import com.example.AsynStudy.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons

class MapFragment: Fragment() {
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root }

    //지도 관련 변수
    private var fm : FragmentManager? = null
    private var mapFragment : MapFragment? = null
    private var naverMap: NaverMap? = null
    private var locationList : List<LocationMarker>? = null

    private val orders : MutableList<OrderMarker> = mutableListOf()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewstart(savedInstanceState)
        databinding(savedInstanceState)
        viewfinal(savedInstanceState)
    }


    private fun viewstart(savedInstanceState: Bundle?) {
        getLocation()
        makeMapView()
    }

    private fun databinding(savedInstanceState: Bundle?) {

    }

    private fun viewfinal(savedInstanceState: Bundle?) {

    }

    // #Custom Functions ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private fun getLocation(){
        locationList = listOf(
            LocationMarker("상차지", LatLng(35.229729, 129.084477)),
        LocationMarker("하차지", LatLng(35.1550124, 129.0524807))
        )
    }

    private fun makeMapView() {
        fm = childFragmentManager
        mapFragment = fm?.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm!!.beginTransaction().add(R.id.map, it).commit()
            }
        /*mapFragment?.let{ it.getMapAsync {} }*/
        // locationList 널값 체킹 먼저 필요할듯. -> null이면 아래들 실행 X.
        mapFragment?.getMapAsync { nMap ->
            setMapSettings(nMap)
            setMarkerSettings(nMap)
        }
    }

    private fun setMapSettings(nMap: NaverMap){
        this.naverMap = nMap.apply {
            uiSettings.isLocationButtonEnabled = false
            uiSettings.isRotateGesturesEnabled = false
            uiSettings.isCompassEnabled = false
            uiSettings.isZoomControlEnabled = false
            locationOverlay.isVisible = false
            locationTrackingMode = LocationTrackingMode.None
            mapType = NaverMap.MapType.Navi
            moveCamera(CameraUpdate.fitBounds(LatLngBounds(locationList!![0].latlng, locationList!![1].latlng)
                , 50, 120, 50, 50).animate(CameraAnimation.Fly, 1000))  //기종 화면에 따른 패딩 값 변경 필요.
            //cameraPosition = CameraPosition(latLng!!, 17.0)
        }
    }

    private fun setMarkerSettings(nMap: NaverMap){
        locationList!!.forEach {
            Marker().apply {
                position = it.latlng
                captionText = it.type
                setCaptionAligns(Align.Top)
                icon = MarkerIcons.BLACK
                width = 50
                height = 70     //기종 화면에 따른 패딩 값 변경 필요.
                captionColor = Color.BLACK
                captionHaloColor = Color.WHITE
                isHideCollidedMarkers = true

                setOnClickListener { o->
                    nMap.moveCamera(CameraUpdate.scrollAndZoomTo(it.latlng, 13.0).animate(CameraAnimation.Fly, 1000))
                    true
                }
                map = nMap
            }
        }

    }

}