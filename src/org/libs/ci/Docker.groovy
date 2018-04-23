package org.libs.ci

/**
 * Docker abrstaction
 */
class Docker implements Serializable {
    static final String REGISTRY = 'localhost'
    static final String PREDEFINED_WORKDIR = '/workspace'
    static final String PREDEFINED_IMAGE = 'ubuntu'
    static final String PREDEFINED_VERSION = 'latest'
    static final String USER = 'admin'
    static final String NAME = 'Docker-${BUILD_TAG}'

    protected String registry
    protected String envs = ""
    protected String volumes = ""
    protected String workdir
    protected String user
    protected String name
    protected String workspace
    protected String image
    protected String version
    protected cmd

    public Docker(Map docker_parameters=[:]){
        this.cmd = new CommandLine()
        this.registry = docker_parameters.registry ? docker_parameters.registry : REGISTRY
        this.workdir = docker_parameters.workdir ? docker_parameters.workdir : PREDEFINED_WORKDIR
        this.workspace = docker_parameters.workspace ? docker_parameters.workspace : PREDEFINED_WORKDIR
        this.image = docker_parameters.image ? docker_parameters.image : PREDEFINED_IMAGE
        this.version = docker_parameters.version ? docker_parameters.version : PREDEFINED_VERSION
        this.user = docker_parameters.user ? docker_parameters.user : USER
        this.name = docker_parameters.name ? docker_parameters.name : NAME
        docker_parameters.envs.each { envar ->
            this.envs += "-e ${envar} \\"
        }
        docker_parameters.volumes.each { volume ->
            this.volumes += "-v ${volume} \\"
        }
    }

    public dockerRun(String command){
        this.cmd.execute("docker run --rm --privileged -t \\" +
                         "${this.envs}" +
                         "${this.volumes}" +
                         "-e WORKSPACE=${this.workspace} \\" +
                         "-v ${this.workspace}=${this.workspace} \\" +
                         "--workdir=${this.workdir} \\" +
                         "--user=${this.user} \\" +
                         "--name=${this.name} \\" +
                         "${this.registry}/${this.image} \\" +
                         "${command}")
    }

    public dockerBuild(){
        this.cmd.execute("docker build ${this.image} -t ${this.registry}/${this.image}:${this.version}")
    }

    public dockerTag(){
        this.cmd.execute("docker tag ${this.image} ${this.registry}/${this.image}:${this.version}")
    }

    public dockerPush(){
        this.cmd.execute("docker push ${this.registry}/${this.image}:${this.version}")
    }
}