
podTemplate(label: 'continuous-delivery-builder', 
  containers: [
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

    stage ('Test') { 
      container('katalon') {
        sh 'katalon-execute.sh -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="test/TS_RegressionTest.ts"'
        //sh 'katalonc.sh -projectPath=. -browserType="Chrome" -retry=0 -statusDelay=15 -testSuitePath="Test Suites/TS_RegressionTest"'
      }
    }
  }
}
