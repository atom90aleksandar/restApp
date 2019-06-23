pipeline {
    agent any 
    stages {
        stage('Clone repo and clean it') { 
            steps {
                bat "rm -rf restApp"
                bat "git clone https://github.com/atom90aleksandar/restApp.git"
                bat "mvn clean -f restApp"
            }
        }
        stage('Test') { 
            steps {
                bat "mvn test -f restApp" 
            }
        }
        stage('Deploy') { 
            steps {
                bat "mvn package -f restApp"
            }
        }
    }
}
