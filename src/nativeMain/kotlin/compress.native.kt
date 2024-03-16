package com.hand.compress

import kotlinx.cinterop.*
import platform.posix.UINT64_MAX

@OptIn(ExperimentalForeignApi::class)
actual fun bz2Enc(data: ByteArray) = memScoped {
    val dataSize = data.size
    val destSize = 1000000
    val dest = allocArray<ByteVar>(destSize)
    val strm = alloc<bz_stream>().apply {
        next_in = data.toCValues().ptr;
        next_out = dest
        avail_in = dataSize.toUInt()
        avail_out = destSize.toUInt()
    }
    var ret = BZ2_bzCompressInit(strm.ptr, 1, 0, 1)
    if (ret != BZ_OK) error("BZ2_bzCompressInit error: $ret")
    ret = BZ2_bzCompress(strm.ptr, BZ_FINISH/*BZ_RUN*/)
    if (ret != BZ_FINISH_OK && ret != BZ_STREAM_END) error("BZ2_bzCompress error: $ret")
    BZ2_bzCompressEnd(strm.ptr)
    dest.readBytes(destSize - strm.avail_out.toInt())
}

@OptIn(ExperimentalForeignApi::class)
actual fun bz2Dec(data: ByteArray) = memScoped {
    val dataSize = data.size
    val destSize = 1000000
    val dest = allocArray<ByteVar>(destSize)
    val strm = alloc<bz_stream>().apply {
        next_in = data.toCValues().ptr;
        next_out = dest
        avail_in = dataSize.toUInt()
        avail_out = destSize.toUInt()
    }
    var ret = BZ2_bzDecompressInit(strm.ptr, 1, 0)
    if (ret != BZ_OK) error("BZ2_bzDecompressInit error: $ret")
    ret = BZ2_bzDecompress(strm.ptr)
    if (ret != BZ_FINISH_OK && ret != BZ_STREAM_END) error("BZ2_bzDecompress error: $ret")
    BZ2_bzDecompressEnd(strm.ptr)
    dest.readBytes(destSize - strm.avail_out.toInt())
}


@OptIn(ExperimentalForeignApi::class)
actual fun bz3Enc(data: ByteArray) = memScoped {
    val dataSize = data.size
    val destSize = alloc<ULongVar>().apply {
        value = bz3_bound(dataSize.convert())
    }
    val dest = allocArray<UByteVar>(destSize.value.toInt())
    val ret = bz3_compress(
        4096.convert(),
        data.toUByteArray().toCValues(),
        dest,
        dataSize.convert(),
        destSize.ptr,
    )
    if (ret != BZ3_OK) error("bz3_compress error: $ret")
    dest.readBytes(destSize.value.toInt())
}

@OptIn(ExperimentalForeignApi::class)
actual fun bz3Dec(data: ByteArray) = memScoped{
    val dataSize = data.size
    val destSize = alloc<ULongVar>().apply {
        value = 1000000u
    }
    val dest = allocArray<UByteVar>(destSize.value.toInt())
    val ret = bz3_decompress(
        data.toUByteArray().toCValues(),
        dest,
        dataSize.toULong(),
        destSize.ptr
    )
    if (ret != BZ3_OK) error("bz3_decompress error: $ret")
    dest.readBytes(destSize.value.toInt())
}

@OptIn(ExperimentalForeignApi::class)
actual fun xzEnc(data:ByteArray) = memScoped {
    val dataSize = data.size
    val destSize = 1000000
    val dest = allocArray<UByteVar>(destSize)
    val strm = alloc<lzma_stream>().apply {
        next_in = data.toUByteArray().toCValues().ptr;
        next_out = dest
        avail_in = dataSize.toULong()
        avail_out = destSize.toULong()
    }
    var ret = lzma_easy_encoder(strm.ptr, 4u, LZMA_CHECK_CRC64)
    if (ret != LZMA_OK) error("lzma_easy_encoder error: $ret")
    ret = lzma_code(strm.ptr, LZMA_FINISH)
    if (ret != LZMA_OK && ret != LZMA_STREAM_END) error("lzma_code error: $ret")
    lzma_end(strm.ptr)
    dest.readBytes(destSize - strm.avail_out.toInt())
}
@OptIn(ExperimentalForeignApi::class)
actual fun xzDec(data:ByteArray) = memScoped {
    val dataSize = data.size
    val destSize = 1000000
    val dest = allocArray<UByteVar>(destSize)
    val strm = alloc<lzma_stream>().apply {
        next_in = data.toUByteArray().toCValues().ptr;
        next_out = dest
        avail_in = dataSize.toULong()
        avail_out = destSize.toULong()
    }
    var ret = lzma_auto_decoder(strm.ptr, UINT64_MAX, LZMA_CONCATENATED)
    if (ret != LZMA_OK) error("lzma_auto_decoder error: $ret")
    ret = lzma_code(strm.ptr, LZMA_FINISH)
    if (ret != LZMA_OK && ret != LZMA_STREAM_END) error("lzma_code error: $ret")
    lzma_end(strm.ptr)
    dest.readBytes(destSize - strm.avail_out.toInt())
}




