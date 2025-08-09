# Selenium Framework Project

## Overview
End-to-end dockerized Selenium project with Allure reporting integration.

**Practice Website**: [Automation Exercise](https://automationexercise.com/)

## Prerequisites
- Java 21
- Maven
- Docker and Docker Compose (for Docker execution)
- Allure Commandline (for local report generation)

## Local Execution

### Running Tests
```bash
# Run all tests
mvn test

# Run with specific profile (e.g., Regression)
mvn test -P Regression
```

### Allure Reporting (Local)

1. **Generate Allure Results**
   ```bash
   mvn clean test -Dallure.results.directory=target/allure-results
   ```

2. **Generate and Serve Allure Report**
   ```bash
   # Generate HTML report
   allure generate target/allure-results -o target/allure-report --clean
   
   # Open report in default browser
   allure open target/allure-report
   ```

3. **Serve Allure Report**
   ```bash
   # Serve report on http://localhost:4040
   allure serve target/allure-results
   ```

## Docker Execution

### Start Selenium Grid and Run Tests
```bash
# Start Selenium Grid and run tests
docker-compose up --build test-runner

# Run in detached mode
docker-compose up -d --build test-runner
```

### Access Allure Reports in Docker

After test execution, Allure reports will be available in the `target/allure-report` directory.

To view the report:

1. **Option 1: Local Machine**
   - The report is available in `./target/allure-report`
   - Open `index.html` in a web browser

2. **Option 2: Serve Report**
   ```bash
   # Navigate to project root
   cd /path/to/project
   
   # Serve the report
   allure serve target/allure-results
   ```

### Viewing Reports for Failed Tests

Even if tests fail, the Allure report will be generated. Check the console output for the report location.

## Additional Commands

### Stop and Cleanup Docker
```bash
# Stop all containers
docker-compose down

# Stop and remove containers, networks, and volumes
docker-compose down -v
```

### Run Specific Test Suite
```bash
# Using Maven (local)
mvn test -Dsurefire.suiteXmlFiles=testSuites/YourTestSuite.xml

# Using Docker
docker-compose run -e TEST_SUITE=YourTestSuite test-runner
```

## Troubleshooting

- **Permission Issues**: If you encounter permission issues with Allure reports in Docker, try:
  ```bash
  chmod -R 777 target/allure-*
  ```

- **Clean Build**: If you encounter issues, try a clean build:
  ```bash
  mvn clean
  rm -rf target/
  docker system prune -f
  docker-compose build --no-cache
  ```

## Extent Report
Extent reports are also available at `target/extent-reports/ExtentReport.html` after test execution.
