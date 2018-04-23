#!groovy

node('master') {
    stage ('Checkout') {
        cleanWs cleanWhenFailure: true
        git credentialsId: JENKINS_BOT_ID, url: REPO
    }

    stage('Generate Docs') {
        def workdir = "src/libs/ci"
        def doctitle = "DSL Shared Libraries Documentation"
        env.JAVA_HOME = "/usr/lib/jvm/java-8-openjdk-amd64"
        env.PATH="${env.JAVA_HOME}/bin:${env.PATH}"

        dir(workdir) {
            sh "groovydoc -classpath src -d dsldocs -windowtitle ${doctitle} -header ${doctitle} -doctitle ${doctitle} **/*.groovy"
            zip archive: true, dir: 'dsldocs', glob: '', zipFile: 'dsldocs.zip'
        }
    }
}