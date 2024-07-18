package com.hand.compress

import com.hand.binary.Binary
import com.hand.binary.FileMode
import com.hand.binary.Path
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals


class Test {
    private val data = "test data"

    @Test
    fun bzip2() {
        bz2Enc(data.encodeToByteArray()).apply {
            assertEquals(data, bz2Dec(this).decodeToString())
        }
    }

    @Test
    fun bzip3(){
        bz3Enc(data.encodeToByteArray()).apply {
            assertEquals(data, bz3Dec(this).decodeToString())
        }
    }

    @Test
    fun xz(){
        xzEnc(data.encodeToByteArray()).apply {
            assertEquals(data, xzDec(this).decodeToString())
        }
    }

    @Test
    fun lz4(){
        lz4Enc(data.encodeToByteArray()).apply {
            assertEquals(data, lz4Dec(this, data.encodeToByteArray().size).decodeToString())
        }
    }
}