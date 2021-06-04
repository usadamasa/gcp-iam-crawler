package dev.usadamasa

import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
import kotlin.test.assertEquals

internal class AccountTest {
    @Test
    fun createActiveUser() {
        val sut = Account.create("user:usadamasa@gmail.com")
        val expected = AccountData("user", "usadamasa@gmail.com", true)
        assertEquals(expected, sut)
    }

    @Test
    fun createDeletedUser() {
        val sut = Account.create("deleted:user:usadamasa@gmail.com")
        val expected = AccountData("user", "usadamasa@gmail.com", false)
        assertEquals(expected, sut)
    }
}
