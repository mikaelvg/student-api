pipeline {

    environment {
        studentRegistry = "mikaelvg/student-api"
        registryCredential = 'dockerhub'
     }

    agent any
    stages {
        stage ('Repo') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'github-account',
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                        sh 'echo uname=$USERNAME pwd=$PASSWORD'
                        git branch: 'master', url: "https://$USERNAME:$PASSWORD@github.com/mikaelvg/student-api.git"
                    }
            }
        }


        stage ('Sonar & Build Package') {
            steps {
                withMaven(
                    maven: 'maven-3',
                    mavenSettingsConfig: 'jenkins-maven-settings') {
                    sh "mvn clean package -Dmaven.test.skip=true"
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
