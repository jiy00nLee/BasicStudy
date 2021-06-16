package com.example.AsynStudy.map

import android.graphics.Color
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons


class OrderMarker(
    var order : Order,
    var latlong: LatLng,
    val onClickListener: (OrderMarker) -> Boolean
) {
    var marker: Marker = Marker().apply {
        position = latlong
        captionText = order.price
        setCaptionAligns(Align.Top)
        icon = MarkerIcons.BLACK
        width = 50
        height = 70     //기종 화면에 따른 패딩 값 변경 필요.
        captionColor = Color.BLACK
        captionHaloColor = Color.WHITE
        isHideCollidedMarkers = true

        setOnClickListener { onClickListener(this@OrderMarker) }
    }
}