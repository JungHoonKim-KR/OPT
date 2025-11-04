# ================= STAGE 1: Build =================
# gradle 이미지를 사용하여 빌드 환경 구성
FROM gradle:8.4-jdk17 AS build
WORKDIR /app

# 1. 의존성 파일만 먼저 복사하여 Docker 캐시 활용 극대화
COPY build.gradle settings.gradle ./
# 2. 의존성 다운로드 (소스 코드 변경 시 이 부분은 캐시로 재사용됨)
RUN gradle build --no-daemon -x test --build-cache || return 0

# 3. 전체 소스 코드 복사
COPY . .

# 4. 애플리케이션 빌드
RUN gradle build --no-daemon -x test


# ================= STAGE 2: Runtime =================
# 더 가벼운 JRE 이미지 사용
# Dockerfile 20번째 줄 수정
FROM openjdk:17-jre-slim-bullseye

WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일만 복사
COPY --from=build /app/build/libs/*.jar /app/OPT.jar

EXPOSE 8080

# ENTRYPOINT는 기존 의도를 존중하여 그대로 두거나, 더 나은 대안을 적용할 수 있습니다.
ENTRYPOINT ["sh", "-c", "sleep 10 && java -jar /app/OPT.jar"]