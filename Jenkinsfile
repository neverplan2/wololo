pipeline {
    agent any
      tools {
        jdk 'OpenJDK 11'
      }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Sonarqube') {
            steps {
                sh './gradlew sonarqube'
            }
        }
    }
}