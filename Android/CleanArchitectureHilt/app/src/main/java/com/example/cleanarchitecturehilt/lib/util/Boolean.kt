package com.example.cleanarchitecturehilt.lib.util

fun Boolean.isTrue(block: () -> Unit) = this.also { if (it) block() }
fun Boolean.isNot(block: () -> Unit) = this.also { if (!it) block() }