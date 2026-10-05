#!/bin/bash

echo "Clonning IMS"
git clone -b android-16-qpr2 https://github.com/Adarsh0127-Elite/android_vendor_mediatek_ims.git vendor/mediatek/ims

echo "Unlock Aperture 4K60"
cd packages/apps/Aperture
git fetch https://github.com/Adarsh0127-Elite/android_packages_apps_Aperture.git
git cherry-pick 9509277efc852ad8bdcce204e0d9cfe104b6d190
cd ../../..

echo "Applying frameworks/native patches"
cd frameworks/native
git fetch https://github.com/oplus-mt6895/android_frameworks_native.git
git cherry-pick 5ae7b13cc9e7359261d15ab185ee4aaeb512f754
git cherry-pick 31f62caf1160bb8460eb7f7c66e279fdb51867d6
cd ../..

echo "Applying frameworks/base patches"
cd frameworks/base
git fetch https://github.com/oplus-mt6895/android_frameworks_base.git
git cherry-pick 8fdf7742a1adabcb5c79fd23dcf2039fcda6ce3b
cd ../..

