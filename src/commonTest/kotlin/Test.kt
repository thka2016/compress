package com.hand.compress

import kotlin.test.Test
import kotlin.test.assertContains


class Test {
    private val data = "test data"

    @Test
    fun bzip2() {
        bz2Enc(data.encodeToByteArray()).apply {
            assertContains(data, bz2Dec(this).decodeToString())
        }
    }

    @Test
    fun bzip3(){
        bz3Enc(data.encodeToByteArray()).apply {
            assertContains(data, bz3Dec(this).decodeToString())
        }
    }

    @Test
    fun xz(){
        xzEnc(data.encodeToByteArray()).apply {
            assertContains(data, xzDec(this).decodeToString())
        }
    }
}