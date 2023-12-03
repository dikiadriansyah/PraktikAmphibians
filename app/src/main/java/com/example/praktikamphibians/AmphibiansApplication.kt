package com.example.praktikamphibians

import android.app.Application
import com.example.praktikamphibians.data.AppContainer
import com.example.praktikamphibians.data.DefaultAppContainer

class AmphibiansApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = DefaultAppContainer()
    }
}