#!/bin/bash 


echo "step 1: test, uber"
clj -T:build ci


echo "step 2: Nativizing it with Graal-VM"

#confguting Graal-vm
export PATH="/home/rt/graalvm-ce-java17-22.3.1/bin:$PATH"
#export CLASSPATH="../target/:$CLASSPATH"


native-image --report-unsupported-elements-at-runtime \
             --initialize-at-build-time \
             --no-fallback \
             -jar ./target/*.jar \
             -H:Name=./grutils-cli

echo "Success! Good to run ./grutils-cli"

