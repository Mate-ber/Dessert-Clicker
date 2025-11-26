package tat.mukhutdinov.lesson9

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import tat.mukhutdinov.lesson9.data.Datasource
import tat.mukhutdinov.lesson9.ui.DessertViewModel

class DessertViewModelTest {

    private val viewModel = DessertViewModel()

    @Test
    fun dessertViewModel_Initialization_InitialStateCorrect() {
        val gameUiState = viewModel.uiState.value

        assertEquals(0, gameUiState.dessertsSold)
        assertEquals(0, gameUiState.revenue)
        assertEquals(Datasource.dessertList.first().price, gameUiState.currentDessertPrice)
        assertEquals(Datasource.dessertList.first().imageId, gameUiState.currentDessertImageId)
    }

    @Test
    fun dessertViewModel_SingleClick_RevenueAndSoldCountUpdated() {
        val initialDessertPrice = Datasource.dessertList.first().price // 5

        viewModel.onDessertClicked()

        val currentGameUiState = viewModel.uiState.value

        assertEquals(initialDessertPrice, currentGameUiState.revenue)
        assertEquals(1, currentGameUiState.dessertsSold)
        assertEquals(Datasource.dessertList.first().price, currentGameUiState.currentDessertPrice)
        assertEquals(Datasource.dessertList.first().imageId, currentGameUiState.currentDessertImageId)
    }

    @Test
    fun dessertViewModel_UpgradeThresholdReached_DessertUpgraded() {
        val initialDessert = Datasource.dessertList.first()
        val nextDessert = Datasource.dessertList[1]
        val upgradeThreshold = nextDessert.startProductionAmount

        val expectedRevenue = upgradeThreshold * initialDessert.price

        repeat(upgradeThreshold) {
            viewModel.onDessertClicked()
        }

        val currentGameUiState = viewModel.uiState.value

        assertEquals(expectedRevenue, currentGameUiState.revenue)
        assertEquals(upgradeThreshold, currentGameUiState.dessertsSold)
        assertEquals(nextDessert.price, currentGameUiState.currentDessertPrice)
        assertEquals(nextDessert.imageId, currentGameUiState.currentDessertImageId)
        assertTrue(currentGameUiState.currentDessertPrice > initialDessert.price)
    }

    @Test
    fun dessertViewModel_ClickedAfterUpgrade_NewPriceUsedForRevenue() {
        val initialDessert = Datasource.dessertList.first()
        val nextDessert = Datasource.dessertList[1]
        val upgradeThreshold = nextDessert.startProductionAmount

        repeat(upgradeThreshold) {
            viewModel.onDessertClicked()
        }

        viewModel.onDessertClicked()

        val expectedRevenue = (upgradeThreshold * initialDessert.price) + nextDessert.price
        val expectedSoldCount = upgradeThreshold + 1

        val currentGameUiState = viewModel.uiState.value

        assertEquals(expectedRevenue, currentGameUiState.revenue)
        assertEquals(expectedSoldCount, currentGameUiState.dessertsSold)
        assertEquals(nextDessert.price, currentGameUiState.currentDessertPrice)
    }

    @Test
    fun dessertViewModel_AllDessertsSold_FinalStateCorrect() {
        val totalDesserts = Datasource.dessertList.size
        var expectedRevenue = 0

        val totalClicks = Datasource.dessertList.last().startProductionAmount + 1

        repeat(totalClicks) {
            val currentState = viewModel.uiState.value
            expectedRevenue += currentState.currentDessertPrice
            viewModel.onDessertClicked()
        }

        val finalDessert = Datasource.dessertList.last()
        val currentGameUiState = viewModel.uiState.value

        assertEquals(expectedRevenue, currentGameUiState.revenue)
        assertEquals(totalClicks, currentGameUiState.dessertsSold)
        assertEquals(finalDessert.price, currentGameUiState.currentDessertPrice)
        assertEquals(finalDessert.imageId, currentGameUiState.currentDessertImageId)
    }
}