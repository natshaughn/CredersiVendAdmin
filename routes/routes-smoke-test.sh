#!/bin/sh

# -----------------------------------------------------------------------------
# Display header
# -----------------------------------------------------------------------------

clear
echo
echo -------------------------------------------------------
echo Smoke tests for the Credersi-vend Admin Route Component
echo -------------------------------------------------------
echo

# -----------------------------------------------------------------------------
# Confirm Java exists
# -----------------------------------------------------------------------------

which java > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo ERROR: Java not detected!
    echo
    echo Unable to detect Java.
    echo Please ensure \"which java\" succeeds first!
    echo
    exit 1
fi

# -----------------------------------------------------------------------------
# Prompt for database credentials
# -----------------------------------------------------------------------------

read -p "Neo4j Aura DB ID: " db_id
read -sp "Neo4j Aura DB Password: " db_password
echo

# -----------------------------------------------------------------------------
# Set environment variables
# -----------------------------------------------------------------------------

export CREDERSI_VEND_ROUTE_SERVER="neo4j+s://$db_id.databases.neo4j.io"
export CREDERSI_VEND_ROUTE_USERNAME="neo4j"
export CREDERSI_VEND_ROUTE_PASSWORD="$db_password"

# -----------------------------------------------------------------------------
# Execute smoke tests
# -----------------------------------------------------------------------------

java -cp "dist/VendRoutes.jar;lib/*" com.credersi_vend.routes.CredersiRoute
