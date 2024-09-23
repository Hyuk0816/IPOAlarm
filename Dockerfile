FROM openjdk:17-jdk-slim

# Node.js와 npm을 설치합니다.
RUN apt-get update && apt-get install -y \
    curl \
    && curl -fsSL https://deb.nodesource.com/setup_16.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# Gradle과 프론트엔드 소스를 복사합니다.
COPY . /app
WORKDIR /app


COPY ./gradlew .
COPY ./gradle gradle

RUN chmod +x ./gradlew
COPY build.gradle .
COPY settings.gradle .


# 프론트엔드를 빌드합니다.
RUN cd frontend && npm install && npm run build

# Gradle로 JAR 파일을 빌드합니다.
RUN ./gradlew bootjar

# JAR 파일을 실행합니다.
CMD ["java", "-jar", "build/libs/IPO-0.0.1-SNAPSHOT.jar"]
