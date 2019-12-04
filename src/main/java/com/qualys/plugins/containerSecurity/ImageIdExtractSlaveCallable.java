package com.qualys.plugins.containerSecurity;

import java.io.IOException;
import java.io.Serializable;

import com.github.dockerjava.api.DockerClient;
import com.qualys.plugins.containerSecurity.util.DockerClientHelper;

import hudson.model.TaskListener;
import jenkins.security.MasterToSlaveCallable;

public class ImageIdExtractSlaveCallable extends MasterToSlaveCallable<String, IOException> implements Serializable{
	private static final long serialVersionUID = -4143159957567745621L;
	private String image;
	private String dockerUrl;
	private String dockerCert;
	private TaskListener listener;
	
	public ImageIdExtractSlaveCallable(String image, String dockerUrl, String dockerCert, TaskListener listener) {
		this.image = image;
		this.dockerUrl = dockerUrl;
		this.dockerCert = dockerCert;
		this.listener = listener;
	}
	
	public String call() throws IOException {
		DockerClientHelper helper = new DockerClientHelper(listener.getLogger());
		DockerClient dockerClient = helper.getDockerClient(dockerUrl, dockerCert);
		return helper.fetchImageId(dockerClient, image);
	}
}