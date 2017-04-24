package com.synisys.util.client;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author ashot.karapetyan on 4/23/17.
 */
public class DockerClientProviderTest {


	@Test
	public void testClientCreation(){
		System.setProperty("DOCKER_HOST", "unix:///var/run/docker.sock");
		DockerClientProvider dockerClientProvider = DockerClientProvider.createProvider();
		DockerClient client = dockerClientProvider.createDockerClient();
		client.pullImageCmd("mysql").withTag("latest").exec(new PullImageResultCallback()).awaitSuccess();


	}
}
