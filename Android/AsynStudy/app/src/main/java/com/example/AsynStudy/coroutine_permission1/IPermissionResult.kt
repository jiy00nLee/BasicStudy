package com.example.AsynStudy.coroutine_permission1

interface IPermissionResult {
    fun handlePermissionResult(statusMap: HashMap<String, PermissionResult>)
}