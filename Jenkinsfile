pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withEnv(['ANDROID_HOME=/opt/android-sdk-3859397']) {
          sh './gradlew build'
        }
      }
    }
  }
}
