#Build stage
FROM clojure:temurin-17-alpine AS builder
ENV CLOJURE_VERSION=1.11.1.1182
RUN mkdir -p /build
WORKDIR /build
COPY deps.edn /build/
RUN clojure -P -T:build
COPY ./ /build
RUN clojure -T:build uberjar

#RUN stage
FROM eclipse-temurin:17-alpine

#Labeling container for easier identification
LABEL org.opencontainers.image.authors="grutils"
LABEL io.github.practicalli.service="grutils Service"
LABEL io.github.practicalli.team="MathUtils Engineering Team"
LABEL version="1.0"
LABEL description="Group utilities CLI"

RUN apk add --no-cache \
    dumb-init~=1.2.5

#configure non root user
RUN addgroup -S grutils && adduser -S grutils -G grutils
RUN mkdir -p /service && chown -R grutils. /service
USER grutils    

#copy uber from builder 
RUN mkdir -p /service
WORKDIR /service
COPY --from=builder /target/grutils.jar /service/grutils.jar

#dumb init ensures TERM  signals are sent to the Java process 
#and all child processes are cleaned up on shutdown.
ENTRYPOINT ["/usr/bin/dumb-init", "--"]
CMD ["java", "-jar", "/service/grutils.jar"]