/**
 * ﻿Copyright (C) 2012-2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the Free
 * Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of the
 * following licenses, the combination of the program with the linked library is
 * not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed under
 * the aforementioned licenses, is permitted by the copyright holders if the
 * distribution is compliant with both the GNU General Public License version 2
 * and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package org.n52.oxf.util.web;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

public class PreemptiveBasicAuthenticationHttpClient extends BasicAuthenticationHttpClient {

	public PreemptiveBasicAuthenticationHttpClient(HttpClient toDecorate) {
		super(toDecorate);
	}

	@Override
	protected HttpRequestInterceptor getAuthenticationIntercepter() {
		return new PreemptiveBasicAuthenticationInterceptor();
	}
	
	
	private final class PreemptiveBasicAuthenticationInterceptor implements HttpRequestInterceptor {

		public void process(HttpRequest request, HttpContext context)
				throws HttpException, IOException {
			HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			
			UserPasswordAuthentication creds;
			synchronized (this) {
				creds = credentials.get(targetHost);	
			}
			
			if (creds != null) {
				UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(creds.getUsername(),
						creds.getPassword());
				AuthScope scope = new AuthScope(targetHost);
				
				getHttpClientToDecorate().getCredentialsProvider().setCredentials(scope, credentials);
				
				AuthCache cache = new BasicAuthCache();
				cache.put(targetHost, new BasicScheme());
				
				context.setAttribute(ClientContext.AUTH_CACHE, cache);
				
				BasicCredentialsProvider credProvider = new BasicCredentialsProvider();
				credProvider.setCredentials(scope, credentials);
				context.setAttribute(ClientContext.CREDS_PROVIDER, credProvider);	
			}
		}
		
	}
	
}
