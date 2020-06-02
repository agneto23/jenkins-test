// import groovy.json.JsonSlurper

// def getVersionTags(username) {
//
//     final APP_NAME = "jenkins-test"
//
//     final USER_NAME = username
//
//     def cmd = [ 'bash', '-c', "curl https://api.bitbucket.org/2.0/repositories/${USER_NAME}/${APP_NAME}/refs/tags".toString()]
//     def result = cmd.execute().text
//
//     def slurper = new JsonSlurper()
//     def json = slurper.parseText(result)
//     def tags = new ArrayList()
//     if (json.values == null || json.values.size == 0)
//       tags.add("unable to fetch tags for ${env.JOB_NAME}")
//     else
//       tags.addAll(json.values.name)
//     return tags.join('\n')
//
// }


pipeline {
    agent {
      kubernetes {
        label 'angular-slave'
        yaml """
          apiVersion: v1
          kind: Pod
          metadata:
            labels:
              jenkins-agent: angular8-jnlp-slave
              jenkins/angular-slave: true
          spec:
            serviceAccountName: cd-jenkins
            containers:
            - name: git
              image: alpine/git:latest
              command:
              - cat
              tty: true
            - name: buildah
              image: quay.io/buildah/stable
              securityContext:
                privileged: true
              command:
              - cat
              tty: true
            - name: openshift-cli
              image: widerin/openshift-cli
              securityContext:
                privileged: true
              command:
              - cat
              tty: true
            - name: nodejs
              image: cdocregpro.pronaca.com/jenkins/angular8-jnlp-slave:1.0.0
              imagePullPolicy: IfNotPresent
              securityContext:
                privileged: true
              command:
              - cat
              tty: true
            imagePullSecrets:
            - name: regopen
          """
      }
    }

    environment {
        IMAGE_TAG = 'latest'
//         PRONACA_CREDS = credentials('pronaca-credentials')
    }

    parameters {
        string(name: 'USERNAME', defaultValue: 'caaguilarn', description: 'Username repository')
    }
    
//     triggers {
//         bitbucketPush()
//     }

    stages {

//         stage('Example') {
//
//             agent any
//
//             steps {
//
//                 script {
//
//                     echo "Hello ${params.USERNAME}"
//
//                 }
//             }
//         }

//         stage("Jira parameters") {
//
//             agent any
//
//             options {
//               timeout(time: 30, unit: 'SECONDS')
//             }
//
//             input {
//                 message "Please Provide Parameters"
//                 ok "Ok"
//                 submitter "alice,bob"
//                 parameters {
//                     string(name: 'KEY_ISSUE', description: 'Key issue', defaultValue: 'KISD-737')
//                 }
//             }
//
//             steps {
//
//                 script {
//
//                     def serverInfo = jiraGetServerInfo site: 'jirakruger', failOnError: true
// //                     echo serverInfo.data.toString()
//
//                     def issueKey = "${KEY_ISSUE}"
//
//                     def comments = jiraGetComments idOrKey: issueKey, site: 'jirakruger', failOnError: true
//                     echo comments.data.toString()
//
//                     def resultComments = new ArrayList()
//
//                     if (comments.data.comments == null || comments.data.comments.size == 0)
//                         resultComments.add("unable to fetch comments for ${env.JOB_NAME}")
//                     else
//                         resultComments.addAll(comments.data.comments.body)
//
//                     echo resultComments.join('\n').toString()
//
//                 }
//
//             }
//         }

//         stage("Deployment parameters") {
//
//             agent any
//
//             options {
//               timeout(time: 30, unit: 'SECONDS')
//             }
//
//             input {
//                 message "Please Provide Parameters"
//                 ok "Ok"
//                 submitter "alice,bob"
//                 parameters {
//                     choice(name: 'VERSION_TAG', choices: getVersionTags("${params.USERNAME}"), description: 'Available versions')
//                 }
//             }
//
//             steps {
//
//                 script {
//
//                     env.VERSION_TAG = "${VERSION_TAG}"
//
//                 }
//
//             }
//         }

//          stage('Build docker') {
// //             agent {
// //               docker {
// //                 image 'maven:3-alpine'
// //               }
// //             }
// //
// //             steps {
// //               sh 'mvn --version'
// //             }
// //
//             steps {
//               container('buildah') {
//                 echo 'Start build docker'
//                 sh 'buildah version'
//                 sh "buildah bud -t mytest ./Dockerfile ."
// //                 sh "docker tag ${env.DOCKER_REGISTRY_IMAGE} ${env.DOCKER_REGISTRY_IMAGE_LATEST}"
//               }
//             }
//           }

          stage('Publish registry image 2') {
            steps {
              container('openshift-cli') {
                script {
//                   openshift.withCluster('https://mbmdes01.pronaca.com:8443') {
//                         echo "Using project: ${openshift.project()}"
                        env.OPENSHIFT_SERVER = sh (
                          script: 'oc whoami --show-server',
                          returnStdout: true
                        ).trim()
//                         sh "oc login -u krug.caguilar -p Pronaca2k21 https://cdt01.pro.pronaca.com:6443"
                        sh "oc login -u msatan -p msatan20 https://mbmdes01.pronaca.com:8443"
//                         env.TOKEN_REGISTRY = sh (
//                           script: 'oc whoami -t',
//                           returnStdout: true
//                         ).trim()
//                   }
                }
              }
            }
          }

          stage('Publish openshift registry') {
            steps {
              container('buildah') {
                script {
                  sh "buildah login -u registry -p eyJhbGciOiJSUzI1NiIsImtpZCI6IjRjRXlwTXNxYXB2Q3I4cTNVOENYX2xZUDVlTzBEZjJObXI4QTNhUVRsNk0ifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJvcGVuc2hpZnQtaW1hZ2UtcmVnaXN0cnkiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlY3JldC5uYW1lIjoicmVnaXN0cnktdG9rZW4tczV0eGYiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoicmVnaXN0cnkiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiJlMDhmNGRmMS0yNzU3LTQxMzItOThkZi0xYmQ2YzdiNDEyM2UiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6b3BlbnNoaWZ0LWltYWdlLXJlZ2lzdHJ5OnJlZ2lzdHJ5In0.QZVexHLq-sDUEzP_NSnA7-3hNnk_Ml5CRClzYdfTxhISfJatzsO_wXwawBvaQ9voNO182poP2wSj0egPKv4pn4_bf1zeZ6ghsWN2sYH6Q1RqqhxhB-HdBFNUL2prwJjtIkhxSRn2sp149mrj1xp5v9nqgVeH_Y1ZviSMvbZqcMDnrIXIwXw97OqRyJpGsL12I6Xkb1R0iDYABzVMjvCmtYU7NeCUQGF5a3ZRuEHu8_bKy8PGV464cn1WEQrT5go7Ii41Kq2M-Lwl36bOt8gFZL0lWI1XiiRks5wcugoPhSo9k3QqmS-9l7qoyiSOxiIuDajdzLmHcZxw8B6qCyxhCg https://cdocregpro.pronaca.com"
                }
              }
            }
          }

//         stage('Docker katalon test') {
//             agent {
//                 kubernetes {
//                       label 'continuous-delivery-builder'
//                       yaml """
//                 apiVersion: v1
//                 kind: Pod
//                 metadata:
//                   labels:
//                     jenkins-agent: katalon-jnlp-slave
//                     jenkins/katalon-slave: true
//                 spec:
//                   containers:
//                   - name: katalon
//                     image: katalonstudio/katalon
//                     imagePullPolicy: IfNotPresent
//                     args:
//                     - cat
//                     tty: true
//                 """
//                 }
//             }
//             steps {
//
//                 container('katalon') {
//
//                     script {
//
//                         sh "pwd"
//
//                         if (fileExists('end-to-end')) {
//
//                             sh "pwd"
//
//                             echo "test version exec: ${env.VERSION_TAG}"
//
//                             echo 'Start katalon'
//
//                             sh 'katalonc.sh -projectPath="./end-to-end" -browserType="Firefox" -retry=0 -statusDelay=15 -testSuitePath="Test Suites/test" -apiKey="ae74a191-2cb0-4cf0-a61e-1b1d4ffd5774" -sendMail=caguilar@ec.krugercorp.com'
//
//                         } else {
//
//                             echo 'No exists directory'
//
//                         }
//
//                     }
//                 }
//
//             }
//         }
    }
    
//     post {
//
// 		success {
// 			echo 'Success job'
// 			slackSend(
// 				channel: "testchannel",
// 				color: "good",
// 				message: ":Success"
// 			)
// 		}
// 		failure {
// 			echo "Error job"
// 			slackSend (
// 				channel: "testchannel",
// 				color: "danger",
// 				message: "Error"
// 			)
// 		}
// 	}
}
