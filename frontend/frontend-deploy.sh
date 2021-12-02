#!/bin/sh
echo Removing any existing backend static folder
rm -rf ../backend/src/main/resources/static > /dev/null 2>&1
echo Clone frontend public as backend static folder 
cp -r ./public ../backend/src/main/resources/static > /dev/null 2>&1
