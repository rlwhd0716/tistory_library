package com.example.cleanarchitecturekoin.lib.util

fun Boolean.isTrue(block: () -> Unit) = this.also { if (it) block() }
fun Boolean.isNot(block: () -> Unit) = this.also { if (!it) block() }