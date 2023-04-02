package com.ellie.jetportfolio.utils

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

internal fun <T> LinkedList<T>.addToQueue(item: T, MAX_SIZE: Int): LinkedList<T> {
    if (this.size >= MAX_SIZE) {
        this.removeFirst()
    }
    this.addLast(item)
    return this
}