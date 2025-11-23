package tat.mukhutdinov.lesson9

import tat.mukhutdinov.lesson9.model.Dessert

object FakeDatasource {

    val dessertList = listOf(
        Dessert(imageId = 0, price = 5, startProductionAmount = 0),
        Dessert(imageId = 0, price = 10, startProductionAmount = 5),
        Dessert(imageId = 0, price = 50, startProductionAmount = 20),
        Dessert(imageId = 0, price = 100, startProductionAmount = 50)
    )
}
