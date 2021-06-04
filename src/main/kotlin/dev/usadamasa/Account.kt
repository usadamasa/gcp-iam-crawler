package dev.usadamasa

interface Account {
    val type: String
    val name: String
    val active: Boolean

    companion object {
        fun create(value: String): Account {
            val elem = value.split(":")
            if (elem.size == 3) {
                return AccountData(elem[1], elem[2], false)
            }
            return AccountData(elem[0], elem[1], true)
        }

    }
}

data class AccountData(
    override val type: String,
    override val name: String,
    override val active: Boolean
) : Account
