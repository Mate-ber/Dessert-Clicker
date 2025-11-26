package tat.mukhutdinov.lesson9.ui

import androidx.annotation.DrawableRes
import tat.mukhutdinov.lesson9.data.Datasource

data class DessertUiState(
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessertIndex: Int = 0,
    val currentDessertPrice: Int = Datasource.dessertList[0].price,
    @DrawableRes val currentDessertImageId: Int = Datasource.dessertList[0].imageId
)