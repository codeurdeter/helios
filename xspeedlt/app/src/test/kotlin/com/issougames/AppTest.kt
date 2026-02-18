package com.issougames

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AppTest {

    private val robot = App()

    @Test
    fun `test example from subject returns optimized result`() {
        val articles = "163841689525773"

        val result = robot.optimizePacking(articles)

        assertEquals("163/81/46/82/9/55/73/7", result)
        assertEquals(8, result.split("/").size, "Devrait utiliser 8 cartons")
    }

    @Test
    fun `test empty input returns empty string`() {
        val result = robot.optimizePacking("")
        assertEquals("", result)
    }

    @Test
    fun `test single item`() {
        val result = robot.optimizePacking("5")
        assertEquals("5", result)
    }

    @Test
    fun `test perfect fits (sums of 10)`() {
        val input = "55123491"
        val result = robot.optimizePacking(input)
        
        assertEquals("55/1234/91", result)
    }

    @Test
    fun `test large items only`() {
        val input = "999"
        val result = robot.optimizePacking(input)
        assertEquals("9/9/9", result)
    }

    @Test
    fun `test capacity constraint never exceeded`() {
        val input = "12345678912345678955555" 
        val result = robot.optimizePacking(input)

        val cartons = result.split("/")
        
        cartons.forEach { cartonStr ->
            val sum = cartonStr.map { it.digitToInt() }.sum()
            assertTrue(sum <= 10, "$cartonStr is too big ($sum)")
        }
    }
}