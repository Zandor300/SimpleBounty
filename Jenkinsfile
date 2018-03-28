pipeline {
  agent {
    docker {
      image 'tomcat:8-jre8'
    }
    
  }
  stages {
    stage('package') {
      steps {
        sh 'mvn package'
      }
    }
  }
}