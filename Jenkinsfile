pipeline{
    agent any
    tools{
        maven "maven"

    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/benjaminCanalesC/AutoFix-Backend']])
                bat "mvn clean install"
            }
        }
        stage("Test"){
            steps{
                    bat "mvn test"
            }
        }
        stage("Build and Push Docker Image"){
            steps{
                    script{
                         withDockerRegistry(credentialsId: 'docker-credentials'){
                            bat "docker build -t bcanales/autofix-backend:latest ."
                            bat "docker push bcanales/autofix-backend:latest"
                        }
                }
            }
        }
    }
}