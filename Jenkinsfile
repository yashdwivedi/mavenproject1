pipeline{
  agent any
  stages {
    stage('Testing') {
      steps {
        echo 'running tests'
        bat 'mvn test'
      }
    }
    stage('Biuld') {
      steps {
        echo 'Building jar files...'
        bat 'mvn package'
      }
    }
  }
}
