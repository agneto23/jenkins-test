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
            imagePullSecrets:
            - name: regdock
          """
      }
    }

    environment {
        IMAGE_TAG = 'latest'
        PRONACA_CREDS = credentials('pronaca-credentials')
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
                        sh "oc login -u msatan -p msatan20 --insecure-skip-tls-verify https://192.168.121.1:8443"
                        env.TOKEN_REGISTRY = sh (
                          script: 'oc whoami -t',
                          returnStdout: true
                        ).trim()
//                   }
                }
              }
            }
          }

          stage('Publish openshift registry') {
            steps {
              container('buildah') {
                script {
                  sh "buildah login -u ${PRONACA_CREDS_USR} -p ${env.TOKEN_REGISTRY} cdocregdes.pronaca.com"
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
