#!/usr/bin/sh
echo "Build..."
cd sunset-widget/
mvn clean install
cd -

#echo "Usuwanie..."
#adb uninstall pl.wtopolski.android.sunsetwidget

echo "Install..."
adb install -r sunset-widget/sunset-widget-app/target/sunset-widget-app.apk

echo "Start..."
adb shell am start -a android.intent.action.MAIN -n pl.wtopolski.android.sunsetwidget/.MainActivity
