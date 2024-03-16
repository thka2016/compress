#!/bin/bash
PREFIX="${PREFIX:-$PWD/static-mingw-x86-64}"
KONAN="C:/Users/thka/.konan"
ARCH=${ARCH:-$(uname -m)}
echo $PREFIX
echo $KONAN
echo $ARCH

./bootstrap.sh
./configure --prefix=$PREFIX "$@"
make
# make install