package com.magnocorrea.sandbox.client2WayAuthentication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
/**
 * A simple 2-way authentication client in Java, with no Aditional libraries required (tested on Java 8)
 * @author https://github.com/emaildomagno
 * 
 * @since 20181207
 *
 */
public class Client {
	private static final String SERVER_HOSTNAME = "localhost";
	private static final int SERVER_PORT = 8444;
	static
	{
		//System.setProperty("javax.net.debug", "all"); // Uncomment to a lot of information
		System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.trustStore", "/tmp/chaves/MyClient.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		System.setProperty("javax.net.ssl.keyStore",  "/tmp/chaves/MyClient.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier() {

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals(SERVER_HOSTNAME)) {
							return true;
						}
						return false;
					}
				});
	}

	public static void main(String[] args) throws Exception {
		String httpsUrl = "https://"+SERVER_HOSTNAME+":" + SERVER_PORT + "/ssl-server/xx.html";
		URL url;
		url = new URL(httpsUrl);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		printContent(con);

	}
	/**
	 * To test Post commands
	 * @param con
	 * @throws Exception
	 */
	private static void sendContent(HttpsURLConnection con) throws Exception{
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "MAGNOCORREA");

		String urlParameters = "{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
	}
	private static void printContent(HttpsURLConnection con) {
		if (con != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
