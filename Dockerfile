#Build stage
FROM clojure:temurin-17-alpine AS stage--builder
RUN export PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin:$PATH"

RUN mkdir /build
COPY . /build
WORKDIR /build

RUN clojure -P -T:build ci
RUN clojure -M:test 
RUN clojure -T:build ci


#Inherit GraalVM image
FROM ghcr.io/graalvm/native-image AS stage--graal
RUN export PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin:$PATH"
RUN mkdir -p /stuff 
WORKDIR /stuff
COPY --from=stage--builder /build/target/*.jar /stuff

RUN native-image --report-unsupported-elements-at-runtime \
             --initialize-at-build-time \
             --no-fallback \
             -jar ./*.jar \
             -H:Name=./gr



ENTRYPOINT ["./gr"]