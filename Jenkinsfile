pipeline {

    environment {
        studentRegistry = "mikaelvg/student-api"
        registryCredential = 'dockerhub'
     }

    agent any
    stages {
        stage ('Clone repo') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'github-account',
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                        sh 'echo uname=$USERNAME pwd=$PASSWORD'
                        git branch: 'master', url: "https://$USERNAME:$PASSWORD@github.com/mikaelvg/student.git"
                    }
            }
        }
        stage ('Build Package') {
            steps {
                withMaven(
                    maven: 'maven-3',
                    mavenSettingsConfig: 'jenkins-maven-settings') {
                    sh "mvn clean package"
                    }
            }
        }

        stage('Docker') {
          steps{
            script {
                docker.withRegistry( '', registryCredential ) {
                    //def studentImage = docker.build studentRegistry + ":$BUILD_NUMBER"
                    def studentImage = docker.build studentRegistry + ":latest"
                    studentImage.push()
                }
            }
          }
        }
    }
}