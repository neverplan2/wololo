pipeline {
    agent any
      tools {
        jdk 'openjdk-11'
      }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Sonarqube') {
            steps {
                sh './gradlew sonarqube '
            }
        }
    }
}