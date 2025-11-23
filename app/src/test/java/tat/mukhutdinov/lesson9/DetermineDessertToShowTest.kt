package tat.mukhutdinov.lesson9

import org.junit.Assert.assertEquals
import org.junit.Test
import tat.mukhutdinov.lesson9.utils.determineDessertToShow

class DetermineDessertToShowTest {

    private val desserts = FakeDatasource.dessertList

    @Test
    fun `returns first dessert when sold is zero`() {
        val result = determineDessertToShow(desserts, dessertsSold = 0)
        assertEquals(desserts[0], result)
    }

    @Test
    fun `returns dessert at its startProductionAmount threshold`() {
        for (dessert in desserts) {
            val result = determineDessertToShow(desserts, dessert.startProductionAmount)
            assertEquals("Expected dessert for threshold ${dessert.startProductionAmount}", dessert, result)
        }
    }

    @Test
    fun `returns previous dessert for amounts between thresholds`() {
        for (i in 0 until desserts.size - 1) {
            val current = desserts[i]
            val next = desserts[i + 1]
            val sold = next.startProductionAmount - 1
            if (sold >= 0) {
                val result = determineDessertToShow(desserts, sold)
                assertEquals("When sold=$sold expected ${current.startProductionAmount} dessert",
                    current, result)
            }
        }
    }
}
