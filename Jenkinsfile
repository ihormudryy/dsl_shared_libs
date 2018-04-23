#!groovy

import org.libs.ci.*

node('master') {
    stage ('Checkout') {
        cleanWs cleanWhenFailure: true
        git branch: "master", credentialsId: "github", url: "https://github.com/ihormudryy/dsl_shared_libs"
    }

    stage('Generate Docs') {
        def workdir = "src/org/libs"
        def doctitle = "DSL Shared Libraries Documentation"
        env.JAVA_HOME = "/usr/lib/jvm/java-8-openjdk-amd64"
        env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"
        def cmd = new CommandLine()
       
        
        dir(workdir) {
            def command = "groovydoc -classpath src -d dsldocs -windowtitle ${doctitle} -header ${doctitle} -doctitle ${doctitle} **/*.groovy"
            cmd.execute(command)
            archiveArtifacts artifacts: "dsldocs/**"
            zip archive: true, dir: 'dsldocs', glob: '', zipFile: 'dsldocs.zip'
        }
    }
}