package com.hand.compress

expect fun bz2Enc(data: ByteArray): ByteArray
expect fun bz2Dec(data: ByteArray): ByteArray

expect fun bz3Enc(data: ByteArray): ByteArray
expect fun bz3Dec(data: ByteArray): ByteArray

expect fun xzEnc(data:ByteArray): ByteArray
expect fun xzDec(data:ByteArray): ByteArray