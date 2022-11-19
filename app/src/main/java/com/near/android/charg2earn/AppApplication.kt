package com.near.android.charg2earn

import android.app.Application
import com.knear.android.service.NearMainService

class AppApplication : Application() {
    lateinit var nearMainService: NearMainService
    val contractName = "ebatte-main.testnet"
    val nftContractName = "ebatte-nft.testnet"
    val privateKey =
        "ed25519:5V7AkH63JXFJ7amBVTaHPg1tiDjEMNSQAkWxAw5pZqqGY7JSXSbxwHb5hXVF7LmQ4oKZna7tZzL3Hyec1cZp2KFE"
    val publicKey = "ed25519:3Mo9TcusdNMDMvHX8duSWErDA9hr5FEceupnUX9Ps92G"

    override fun onCreate() {
        super.onCreate()
        nearMainService = NearMainService(this)
    }
}