package com.synisys.util.client;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Provide to create docker client
 *
 * @author david.petrosyan
 */
public class DockerClientProvider {

	private String dockerHost;

	private boolean isTlsVerify;

	private String dockerCertPath;

	private boolean isDockerHostLocalhost;

	private DockerClientProvider(String dockerHost, String dockerCertPath, boolean isTlsVerify) {
		this.dockerHost = dockerHost;
		if (dockerHost.contains("localhost")) {
			this.isDockerHostLocalhost = true;
		}
		this.dockerCertPath = dockerCertPath;
		this.isTlsVerify = isTlsVerify;
	}

	public static DockerClientProvider createProvider() {
		String dockerHost = System.getProperty("DOCKER_HOST");

		boolean isTlsVerify = Boolean.valueOf(System.getProperty("DOCKER_TLS_VERIFY"));

		String dockerCertPath = System.getProperty("DOCKER_CERT_PATH");
		return new DockerClientProvider(dockerHost, dockerCertPath, isTlsVerify);
	}


	/**
	 * Create DockerClient' instance
	 */
	public DockerClient createDockerClient() {

		DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig
				.createDefaultConfigBuilder();
		if (!this.isDockerHostLocalhost) {
			configBuilder.withDockerHost(dockerHost);
		}
		if (isTlsVerify) {
			configBuilder.withDockerTlsVerify(true);
			configBuilder.withDockerCertPath(dockerCertPath);

			//TODO: remove
//			configBuilder.withRegistryUrl("https://registry.arm.synisys.com");
//			configBuilder.withRegistryUsername("snapshotsvc");
//			configBuilder.withRegistryPassword("snapshotsvc");
//			configBuilder.withRegistryEmail("snapshotsvc@mail.ru");
		}

		return DockerClientBuilder
				.getInstance(configBuilder.build())
				.build();
	}

	/**
	 * @return docker machine host domain
	 */
	private String getHost() {
		return this.isDockerHostLocalhost ? "localhost" : getDomainFromDockerHostUri();
	}

	/**
	 * @return domain from docker host
	 */
	private String getDomainFromDockerHostUri() {
		try {
			return new URI(dockerHost).getHost();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
