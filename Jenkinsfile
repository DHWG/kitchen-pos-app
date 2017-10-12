pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        echo 'Hello World'
      }
    }
    stage('Build') {
      steps {
        sh './gradlew build'
      }
    }
  }
}