#!/bin/bash


PATH_TO_PLUGIN=~/protoc-gen-grpc-java-1.33.1-osx-x86_64.exe
SRC_DIR=src/main/proto/
DST_DIR=src/main/java/
protoc --plugin=protoc-gen-grpc-java=$PATH_TO_PLUGIN -I=$SRC_DIR \
--java_out=$DST_DIR --grpc-java_out=$DST_DIR Mock.proto
