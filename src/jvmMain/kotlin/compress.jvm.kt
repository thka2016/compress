package com.hand.compress

import net.jpountz.lz4.LZ4Factory
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream
import org.tukaani.xz.XZInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

actual fun bz2Enc(data: ByteArray) =
    ByteArrayInputStream(data).use { inStream->
        val dest = ByteArrayOutputStream()
        BZip2CompressorOutputStream(dest.buffered()).use { outSteam->
            ByteArrayOutputStream().buffered()
            inStream.copyTo(outSteam)
        }
        dest.toByteArray()
    }

actual fun bz2Dec(data: ByteArray) =
    BZip2CompressorInputStream(ByteArrayInputStream(data).buffered()).use { inStream->
        ByteArrayOutputStream().use { outSteam->
            inStream.copyTo(outSteam)
            outSteam.toByteArray()
        }
    }


actual fun bz3Enc(data: ByteArray): ByteArray {
    TODO("Not yet implemented")
}

actual fun bz3Dec(data: ByteArray): ByteArray {
    TODO("Not yet implemented")
}


actual fun xzEnc(data: ByteArray) =
    ByteArrayInputStream(data).use { inStream->
        val dest = ByteArrayOutputStream()
        XZCompressorOutputStream(dest.buffered()).use { outSteam->
            ByteArrayOutputStream().buffered()
            inStream.copyTo(outSteam)
        }
        dest.toByteArray()
    }

actual fun xzDec(data:ByteArray) =
    XZInputStream(ByteArrayInputStream(data).buffered()).use { inStream->
        ByteArrayOutputStream().use { outSteam->
            inStream.copyTo(outSteam)
            outSteam.toByteArray()
        }
    }

actual fun lz4Enc(data: ByteArray) =
    LZ4Factory.fastestJavaInstance().fastCompressor().compress(data)

actual fun lz4Dec(data: ByteArray, srcSize: Int) =
    LZ4Factory.fastestJavaInstance().fastDecompressor().decompress(data, srcSize)
