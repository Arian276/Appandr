#!/usr/bin/env sh
# Gradle wrapper launcher
APP_BASE_NAME=`basename "$0"`
APP_HOME=`dirname "$0"`
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
if [ -n "$JAVA_HOME" ] ; then
    JAVA_HOME_BIN="$JAVA_HOME/bin/java"
    exec "$JAVA_HOME_BIN" -Xmx64m -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
else
    exec java -Xmx64m -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
fi
