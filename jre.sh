#!/bin/sh


sudo cp -r /usr/lib/jvm/java-7-openjdk-amd64/ /dev/shm/
xcape
xcape -e 'Shift_L=Escape'
xcape -e 'Shift_R=Control_L|space'
xcape -e 'Control_R=Control_L|S'


