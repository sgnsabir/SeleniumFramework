#!/bin/bash
set -euo pipefail

# Set default values
export HUB_HOST=${HUB_HOST:-selenium-hub}
export BROWSER=${BROWSER:-chrome}
export THREAD_COUNT=${THREAD_COUNT:-1}
export TEST_SUITE=${TEST_SUITE:-testng}
export ALLURE_RESULTS_DIR=${ALLURE_RESULTS_DIR:-/app/target/allure-results}
export ALLURE_REPORT_DIR=${ALLURE_REPORT_DIR:-/app/target/allure-report}

# Print configuration
echo "==========================================="
echo "  Selenium Grid Test Runner"
echo "  $(date)"
echo "==========================================="
echo "HUB_HOST:      ${HUB_HOST}"
echo "BROWSER:       ${BROWSER}"
echo "THREAD_COUNT:  ${THREAD_COUNT}"
echo "TEST_SUITE:    ${TEST_SUITE}"
echo "WORKING_DIR:   $(pwd)"
echo "JAVA_HOME:     ${JAVA_HOME:-Not Set}"
echo "==========================================="

# Check hub readiness
check_hub_ready() {
    echo "Checking Selenium Hub status at http://${HUB_HOST}:4444/status"
    local max_attempts=30
    local attempt=0

    until curl -s "http://${HUB_HOST}:4444/status" | jq -e '.value.ready == true' >/dev/null; do
        attempt=$((attempt + 1))
        if [ $attempt -ge $max_attempts ]; then
            echo "ERROR: Selenium Hub not ready after ${max_attempts} attempts"
            return 1
        fi
        echo "Waiting for Hub... (${attempt}/${max_attempts})"
        sleep 2
    done
    echo "Selenium Hub is ready!"
}

# Check node registration
check_node_registered() {
    local browser=$1
    echo "Checking for ${browser} node registration"
    local max_attempts=20
    local attempt=0

    until curl -s "http://${HUB_HOST}:4444/status" | \
          jq -e ".value.nodes[] | select(.slots[] | .stereotype.browserName == \"${browser}\")" >/dev/null; do
        attempt=$((attempt + 1))
        if [ $attempt -ge $max_attempts ]; then
            echo "ERROR: ${browser} node not registered after ${max_attempts} attempts"
            return 1
        fi
        echo "Waiting for ${browser} node... (${attempt}/${max_attempts})"
        sleep 2
    done
    echo "${browser} node is registered!"
}

# Run tests
run_tests() {
    echo "Starting test execution"
    mkdir -p ${ALLURE_RESULTS_DIR}

    # Build Maven command
    local mvn_command="mvn -B test \
        -Dselenium.grid.url=http://${HUB_HOST}:4444 \
        -Dbrowser=${BROWSER} \
        -Dthread.count=${THREAD_COUNT} \
        -Dsurefire.suiteXmlFiles=testSuites/${TEST_SUITE}.xml \
        -Dallure.results.directory=${ALLURE_RESULTS_DIR} \
        -Dmaven.repo.local=/app/.m2/repository \
        --no-transfer-progress"

    echo "Executing: ${mvn_command}"
    eval ${mvn_command} || true  # Continue even if tests fail

    # Generate Allure report
    if [ -d "${ALLURE_RESULTS_DIR}" ] && [ "$(ls -A ${ALLURE_RESULTS_DIR})" ]; then
        echo "Generating Allure report..."
        allure generate ${ALLURE_RESULTS_DIR} --clean -o ${ALLURE_REPORT_DIR} || \
        echo "Warning: Allure report generation failed"
    else
        echo "Warning: No test results found for Allure report"
    fi
}

# Main execution
main() {
    # Verify required tools
    command -v java >/dev/null || { echo "Java not found"; exit 1; }
    command -v mvn >/dev/null || { echo "Maven not found"; exit 1; }
    command -v curl >/dev/null || { echo "curl not found"; exit 1; }
    command -v jq >/dev/null || { echo "jq not found"; exit 1; }
    command -v allure >/dev/null || { echo "allure not found"; exit 1; }

    check_hub_ready
    check_node_registered "${BROWSER}"
    run_tests

    echo "Test execution completed"
    exit 0
}

main