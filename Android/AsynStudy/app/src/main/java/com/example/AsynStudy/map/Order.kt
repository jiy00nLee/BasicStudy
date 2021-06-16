package com.example.AsynStudy.map


data class Order(
    var id: Int = 0,
    val bookingNumber: String = "",
    var date: String = "",
    val time: String = "",
    val departureAddress: String = "",
    val departureFloorAndElevator: String = "",
    val arrivalAddress: String = "",
    val arrivalFloorAndElevator: String = "",
    var price: String = "",
    val truck: String? = "",
    val distance: String? = "",
    val workDetail: String = "",
    val loadDetail: String = "",
    val worker: String = "",
    //val memo: String = "",
    val wayPoints: ArrayList<Waypoint> = ArrayList(),
    val status: Status? = Status.ACCEPT
) {


    enum class Status {
        ASSIGN, ACCEPT, CANCEL_ACCEPT, REJECT, DONE
    }
}