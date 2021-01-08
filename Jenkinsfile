pipeline {
  environment {
   	 PROJECT = "cwpipelinestudent"
 	   APP_NAME = "cw01student"
     BRANCH_NAME = "dev_st_branch"
     PORT = "5070"
   	 IMAGE_TAG = "${PROJECT}/${APP_NAME}:${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
                }
    agent any 
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
         steps {
            sh "mvn -Dmaven.test.skip=true clean install -X"
          }
        }
  	stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deliver') {
          steps {
               sh 'bash ./jenkins/scripts/runtest.sh'
               sh 'bash ./jenkins/scripts/kill.sh'
         }
        }
		stage('Building & Deploy Image') {
		    steps{
					sh 'mkdir -p dockerImage'
					sh 'cp Dockerfile dockerImage/'
					sh 'cp target/student-0.0.1-SNAPSHOT.jar dockerImage/'
					sh 'docker build --tag=${APP_NAME} dockerImage/.'
					sh 'docker tag ${APP_NAME} ${IMAGE_TAG}'
					
          sh 'rm -rf dockerImage/'          
        }
        }
        stage('Deploy cluster') {
              steps {
                  sh 'mkdir -p /var/lib/jenkins/.kube/'
                  sh '''cat <<EOF > deployment.yaml
apiVersion: apps/v1                  
kind: Deployment
metadata:
  name: ${APP_NAME}-deploy
spec:
  selector:
    matchLabels:
      app: ${APP_NAME}-deploy
      department: stage
  replicas: 1
  template:
    metadata:
      labels:
        app: ${APP_NAME}-deploy
        department: stage
    spec:
      containers:
      - name: ${APP_NAME}
        image: ${IMAGE_TAG}
        env:
        - name: "PORT"
          value: "${PORT}"
EOF'''
               sh 'kubectl apply -f deployment.yaml'
               sh '''cat <<EOF > service.yaml
apiVersion: v1
kind: Service
metadata:
  name: ${APP_NAME}-service
spec:
  selector:
    app: ${APP_NAME}-deploy
  ports:
    - name: http
      protocol: TCP
      port: ${PORT}
      targetPort: ${PORT}
  externalIPs:
    - 192.168.175.20
EOF'''
               sh 'kubectl apply -f service.yaml'               
                  }
        }
            } 
}
