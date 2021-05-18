package com.example.manager.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalConfig {
    /**
     * application.yml 에서 설정한 upload path
     */
    @Value("${serverInfo.upload_path}")
    private String uploadFilePath;

    /**
     * application.yml 에서 설정한 base url
     */
    @Value("${serverInfo.base_url}")
    private String baseUrl;

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
