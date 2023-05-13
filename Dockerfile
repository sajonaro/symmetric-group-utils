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
             -H:Name=./grutils-cli


#final (prod/run) stage
FROM eclipse-temurin:17-alpine AS stage--prod
RUN export PATH="/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin:/root/bin:$PATH"

#Labeling container for easier identification
LABEL org.opencontainers.image.authors="grutils"
LABEL io.github.practicalli.service="grutils Service"
LABEL io.github.practicalli.team="MathUtils Engineering Team"
LABEL version="1.0"
LABEL description="Group utilities CLI"

RUN apk add --no-cache \
    dumb-init~=1.2.5

#configure non root user
##RUN addgroup -S grutils && adduser -S grutils -G grutils
##RUN mkdir -p /service && chown -R grutils. /service
##USER grutils    

#copy uber from graalVM stage 
RUN mkdir -p /service
WORKDIR /service
COPY --from=stage--graal /stuff/grutils-cli  /service
RUN chmod +x grutils-cli

#dumb init ensures TERM  signals are sent to the Java process 
#and all child processes are cleaned up on shutdown.
#ENTRYPOINT ["/usr/bin/dumb-init", "--"]
#CMD ["java", "-jar", "/service/grutils-cli"]

ENTRYPOINT ["./grutils-cli"]