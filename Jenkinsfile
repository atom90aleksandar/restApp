pipeline {
    agent any 
    stages {
        stage('---clean---') { 
            steps {
                bat "mvn clean"
            }
        }
        stage('---test---') { 
            steps {
                bat "mvn test" 
            }
        }
        stage('---package---') { 
            steps {
                bat "mvn package"
            }
        }
        stage('Clone helloworld git repository and compile HelloWorld.java'){
             steps{
                checkout([$class: 'GitSCM', branches: [[name: 'developer_branch']], 
                          doGenerateSubmoduleConfigurations: false, extensions: 
                          [[$class: 'CloneOption', depth: 0, noTags: true, reference: '', shallow: false, timeout: 60], 
                          [$class: 'SubmoduleOption', disableSubmodules: false, parentCredentials: false, recursiveSubmodules: true, reference: '',
                          timeout: 60, trackingSubmodules: true], [$class: 'CheckoutOption', timeout: 60]], submoduleCfg: [], userRemoteConfigs:
                          [[url: 'https://github.com/atom90aleksandar/helloworld.git']]])
                dir('helloworld') {
                       bat "javac HelloWorld.java"
                       bat "java HelloWorld"
               }
             }
       }
    }
}
