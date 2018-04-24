package org.libs.ci

import hudson.plugins.git.GitSCM
import hudson.plugins.git.BranchSpec
import hudson.plugins.git.SubmoduleConfig
import hudson.plugins.git.extensions.GitSCMExtension

import com.cloudbees.jenkins.GitHubPushTrigger
import com.coravy.hudson.plugins.github.GithubProjectProperty
import com.google.common.collect.Collections2
import com.google.common.collect.Lists
import org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition
import org.jenkinsci.plugins.workflow.job.WorkflowJob

def createSimplePipelineJob(repoName, jobName, credId, jenkinsfile) {
    def jenkins = Jenkins.instance
    def gitTrigger = new GitHubPushTrigger()
    def projectProperty = new GithubProjectProperty(repoName)
    def repo_list = GitSCM.createRepoList(repoName, credId)
    def scm =  new GitSCM(repo_list,
                          [new BranchSpec("*/master")],
                          false,
                          Collections.<SubmoduleConfig>emptyList(),
                          null, null,
                          Collections.<GitSCMExtension>emptyList()
                          )
    def job = new WorkflowJob(jenkins, jobName)
    job.setDefinition(new CpsScmFlowDefinition(scm, jenkinsfile))
    job.setTriggers([gitTrigger])
    job.addProperty(projectProperty)
    jenkins.reload()
}