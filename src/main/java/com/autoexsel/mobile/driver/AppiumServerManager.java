package com.autoexsel.mobile.driver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.autoexsel.data.manager.JSONLoader;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerManager {
	private AppiumDriverLocalService service = null;
	private AppiumServiceBuilder builder = null;
	private String appiumHost = null;

	public String startAppiumServer() throws Exception {
		String ipAddress = "";
		String port = "";
		JSONObject config = JSONLoader.config;
		
		if (config.isNull("appiumServer")) {
			appiumHost = startDefaultServer();
		} else {
			if (!config.getJSONObject("appiumServer").isNull("ipAddress")) {
				ipAddress = config.getJSONObject("appiumServer").getString("ipAddress");
			}
			if (!config.getJSONObject("appiumServer").isNull("port")) {
				port = config.getJSONObject("appiumServer").getString("port");
			}
			if (ipAddress == "" || port == "") {
				appiumHost = startDefaultServer();
			} else {
				if(checkIfServerIsRunnning(Integer.parseInt(port))) {
					appiumHost = "http://"+ipAddress+":"+port+"/wd/hub";
				}else {
					appiumHost = startServer(ipAddress, Integer.parseInt(port));
				}
			}
		}
		System.out.println("Appium server is started on host: " + appiumHost);
		return appiumHost;

	}

	private String startDefaultServer() {
		// Start the server with default address and port.
		service = AppiumDriverLocalService.buildDefaultService();
		AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"));
		service = builder.build();
		service.start();
		System.out.println("Starting default server , please wait ......");
		service.start();
		return service.getUrl().toString();
	}

	private String startServer(String ipAddress, int port) {
		// Set Capabilities
		DesiredCapabilities cap;
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");

		// Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress(ipAddress);
		builder.usingPort(port);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
		builder.withArgument(GeneralServerFlag.LOG_LEVEL, "error");

		System.out.println("checkIfServerIsRunnning on " + port + ": " + checkIfServerIsRunnning(port));
		// Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
		System.out.println("Starting appium server , please wait ......");
		return service.getUrl().toString();
	}

	public void stopServer() {
		if(service != null) {
			service.stop();
		}
	}

	public String getServerURL() {
		return service.getUrl().toString();
	}

	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			try {
				if (serverSocket != null)
					serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverSocket = null;
		}
		return isServerRunning;
	}

	public void sleep(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
