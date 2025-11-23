package tat.mukhutdinov.lesson9

import org.junit.Assert.assertEquals
import org.junit.Test
import tat.mukhutdinov.lesson9.utils.determineDessertToShow

class DetermineDessertTest {

    private val desserts = FakeDatasource.dessertList

    @Test
    fun `returns first dessert when count is zero`() {
        val result = determineDessertToShow(desserts, 0)
        assertEquals(desserts[0], result)
    }

    @Test
    fun `returns second dessert when sold count reaches its threshold`() {
        val result = determineDessertToShow(desserts, 5)
        assertEquals(desserts[1], result)
    }

    @Test
    fun `returns last dessert for very large number of sold desserts`() {
        val result = determineDessertToShow(desserts, 1000)
        assertEquals(desserts.last(), result)
    }
}
