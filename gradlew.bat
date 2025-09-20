\
@ECHO OFF
set CLASSPATH=%~dp0\gradle\wrapper\gradle-wrapper.jar
IF NOT "%JAVA_HOME%"=="" (
  "%JAVA_HOME%\bin\java.exe" -Xmx64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
) ELSE (
  java -Xmx64m -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
)
