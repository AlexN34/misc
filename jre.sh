#!/bin/sh


stty ixany
sudo cp -r /usr/lib/jvm/java-7-openjdk-amd64/ /dev/shm/
xcape
xcape -e 'Shift_L=Escape'
xcape -e 'Shift_R=Control_L|space'
xcape -e 'Control_R=Control_L|S'
xcape -e 'Control_L=space|w'
./blackwidow_enable.py

# Blackwidow macro keys and matching code:
# m1: 191 XF86Tools (returns keycode 179)
# m2: 192 XF86Launch5 
# m3: 193 XF86Launch6 
# m4: 194 XF86Launch7
# m5: 195 XF86Launch8
