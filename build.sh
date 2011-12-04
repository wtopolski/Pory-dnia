#!/usr/bin/sh
echo "Build..."
mvn clean install

#echo "Usuwanie..."
adb uninstall pl.wtopolski.android.sunsetwidget

echo "Install..."
adb install -r target/sunset-widget.apk

echo "Start..."
adb shell am start -a android.intent.action.MAIN -n pl.wtopolski.android.sunsetwidget/.MainActivity
