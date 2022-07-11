package com.cloud.cang.weixin.client;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * httpclient 4.3.x
 * @author Yi
 *
 */
public class HttpClientFactory{

	public static HttpClient createHttpClient() {
		try {
			SSLContext sslContext = null;

			  sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

			    @Override
			    public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
			      throws CertificateException {

			      return true;
			    }
			  }).build();
			
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
			  .<ConnectionSocketFactory> create()
			  .register("http", PlainConnectionSocketFactory.getSocketFactory())
			  .register("https", sslSocketFactory).build();
			PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
			  socketFactoryRegistry);
			return HttpClientBuilder.create().setSslcontext(sslContext).setConnectionManager(connMgr)
			  .build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HttpClient createHttpClient(int maxTotal,int maxPerRoute) {
		try {
			SSLContext sslContext = null;

			  sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

			    @Override
			    public boolean isTrusted(final X509Certificate[] arg0, final String arg1)
			      throws CertificateException {

			      return true;
			    }
			  }).build();
			
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext,
					SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
			  .<ConnectionSocketFactory> create()
			  .register("http", PlainConnectionSocketFactory.getSocketFactory())
			  .register("https", sslSocketFactory).build();
			PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
			  socketFactoryRegistry);
			connMgr.setMaxTotal(maxTotal);
			connMgr.setDefaultMaxPerRoute(maxPerRoute);
			return HttpClientBuilder.create().setSslcontext(sslContext).setConnectionManager(connMgr)
			  .build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * Key store 类型HttpClient
	 * @param keystore
	 * @param keyPassword
	 * @param supportedProtocols
	 * @param maxTotal
	 * @param maxPerRoute
	 * @return
	 */
	public static HttpClient createKeyMaterialHttpClient(KeyStore keystore,String keyPassword,String[] supportedProtocols) {
		try {
			SSLContext sslContext = SSLContexts.custom().useSSL().loadKeyMaterial(keystore, keyPassword.toCharArray()).build();
			SSLConnectionSocketFactory sf = new SSLConnectionSocketFactory(sslContext,supportedProtocols,
	                null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			return HttpClientBuilder.create().setSSLSocketFactory(sf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}


}
