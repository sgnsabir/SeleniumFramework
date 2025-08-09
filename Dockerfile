# Use official Java 21 image
FROM eclipse-temurin:21-jdk-jammy

# Set environment variables
ENV BROWSER=chrome \
    HUB_HOST=selenium-hub \
    HUB_PORT=4444 \
    THREAD_COUNT=1 \
    TEST_SUITE=testng \
    IS_DOCKER=true \
    MAVEN_OPTS="-Dmaven.repo.local=/app/.m2/repository" \
    HOME=/app

# Install essential packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    curl \
    jq \
    git \
    maven \
    unzip \
    wget \
    xvfb && \
    rm -rf /var/lib/apt/lists/*

# Install Allure
RUN wget -q https://github.com/allure-framework/allure2/releases/download/2.28.0/allure-2.28.0.tgz && \
    tar -zxvf allure-2.28.0.tgz -C /opt/ && \
    ln -s /opt/allure-2.28.0/bin/allure /usr/bin/allure && \
    rm allure-2.28.0.tgz && \
    allure --version

# Create app directory
WORKDIR /app

# Create required directories
RUN mkdir -p /app/target/{surefire-reports,screenshots,allure-results,allure-report} \
    && mkdir -p /app/testSuites \
    && chmod -R 777 /app/target

# Copy runner script and make executable
COPY runner.sh /app/runner.sh
RUN chmod +x /app/runner.sh

# Copy project files
COPY pom.xml .
COPY src ./src/
COPY testSuites ./testSuites/

# Build project dependencies
RUN mvn -B dependency:go-offline dependency:resolve-plugins

# Build the project
RUN mvn -B clean compile test-compile

# Set entrypoint
ENTRYPOINT ["/app/runner.sh"]