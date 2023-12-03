package com.example.praktikamphibians.data

import com.example.praktikamphibians.model.Amphibian
import com.example.praktikamphibians.network.AmphibiansApiService

interface AmphibiansRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class DefaultAmphibiansRepository(
    private  val amphibiansApiService: AmphibiansApiService
) : AmphibiansRepository{
    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}