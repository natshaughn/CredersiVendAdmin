#!/bin/sh
echo Removing any existing backend lib folder
rm -rf ../backend/lib > /dev/null 2>&1
echo Clone routes lib as backend folder 
cp -r ./lib ../backend/lib > /dev/null 2>&1
echo Deploy routes dist JAR to backend
cp -r ./dist/VendRoutes.jar ../backend/lib > /dev/null 2>&1
