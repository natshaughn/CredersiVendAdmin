!/bin/sh

# -----------------------------------------------------------------------------
# Display header
# -----------------------------------------------------------------------------

clear
echo
echo ---------------------------------------------------------
echo Generate docs for the Credersi-vend Admin Route Component
echo ---------------------------------------------------------
echo

# -----------------------------------------------------------------------------
# Confirm the Java Documentation compiler exists
# -----------------------------------------------------------------------------

which javadoc > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo ERROR: Java Documentation compiler not detected!
    echo
    echo Unable to detect the Java Documentation compiler.
    echo Please ensure \"which javadoc\" succeeds first!
    echo
    exit 1
fi

# -----------------------------------------------------------------------------
# Compile Java Documentation
# -----------------------------------------------------------------------------

javadoc -cp "lib/*" -d doc $(find ./src -name '*.java')
