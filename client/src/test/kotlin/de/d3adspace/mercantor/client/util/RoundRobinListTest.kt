package de.d3adspace.mercantor.client.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author Felix Klauke <fklauke@itemis.de>
 */
class RoundRobinListTest {

    private val source = ArrayList<Int>()
    private val list = RoundRobinList(source)

    @Before
    fun setUp() {
        list.add(0)
        list.add(2)
        list.add(1)
        list.add(3)
    }

    @Test
    fun isEmpty() {
        assert(!list.isEmpty)
        list.remove(0)
        list.remove(1)
        list.remove(2)
        list.remove(3)
        assert(list.isEmpty)
    }

    @Test
    fun getContent() {
        assertArrayEquals(source.toIntArray(), list.getContent().toIntArray())
    }

    @Test
    fun size() {
        assertTrue(list.size() == 4)
        assertTrue(list.size() == source.size)
        list.remove(0)
        assertTrue(list.size() == 3)
    }

    @Test
    fun add() {
        list.add(5)
        assertTrue(list.size() == 5)
        assertTrue(list.getContent().contains(5))
    }

    @Test
    fun get() {
        for (i in 1..100) {
            val value = list.get()
            assertEquals(source[(i - 1) % 4], value)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun getWhileEmpty() {
        source.clear()

        list.get()

        fail()
    }

    @Test
    fun remove() {
        list.remove(3)
        assertTrue(!source.contains(3))
        assertTrue(list.size() == 3)
    }
}