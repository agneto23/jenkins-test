
podTemplate(label: 'continuous-delivery-builder', 
  containers: [
    containerTemplate(
      name: 'jnlp',
      image: 'jenkinsci/jnlp-slave:3.10-1-alpine',
      args: '${computer.jnlpmac} ${computer.name}'
    ),
    containerTemplate(
      name: 'alpine',
      image: 'twistian/alpine:latest',
      command: 'cat',
      ttyEnabled: true
    ),
    containerTemplate(
      name: 'katalon',
      image: 'katalonstudio/katalon',
      command: 'cat',
      ttyEnabled: true
    ),
  ],
  volumes: [ 
    hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
  ]
)
{
  node ('continuous-delivery-builder') {

 //   stage('Apply Kubernetes files') {
 //     withKubeConfig([credentialsId: 'devops-k8s', ]) {
 //       sh 'kubectl config view'
        // switch context from default to target environment
        // sh "kubectl config use-context ${contextName}"
        // deploy the resources (without pushing)
        // sh 'kubectl apply -k .'
        // wait for deployment to complete
        // sh "kubectl rollout status deployment/${projectName} --timeout=2m"
 //     }
 //   }
//     stage('Apply Kubernetes files') {
//       steps {
//         withKubeConfig([credentialsId: 'devops-k8s' , ]) {
//             sh 'kubectl get pods'
//             sh 'helm get mkp-int --tiller-namespace mkp-int'
//           }
//       }
//     }

    stage ('Test') { 
      container('katalon') {
        sh 'katalon-execute.sh -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="test/TS_RegressionTest"'
        //sh 'katalonc.sh -projectPath=. -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="Test Suites/TS_RegressionTest"'
      }
    }


  }
}
