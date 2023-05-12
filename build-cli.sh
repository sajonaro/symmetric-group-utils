#!/bin/bash 


echo "step 0: run unit tests "
clj -M:test


echo "step 1: clean, generate uber jar"
clj -T:build ci


echo "step 2: create native image of it with Graal-VM"

#confguting Graal-vm
export PATH="/home/rt/graalvm-ce-java17-22.3.1/bin:$PATH"
#export CLASSPATH="../target/:$CLASSPATH"


native-image --report-unsupported-elements-at-runtime \
             --initialize-at-build-time \
             --no-fallback \
             -jar ./target/*.jar \
             -H:Name=./grutils-cli


FILE=./grutils-cli
if test -f "$FILE"; then
    echo "Success! Good to run ./grutils-cli"
fi



