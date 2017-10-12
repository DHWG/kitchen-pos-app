pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        sh 'export ANDROID_HOME="/opt/android-sdk-3859397"'
      }
    }
    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }
  }
}