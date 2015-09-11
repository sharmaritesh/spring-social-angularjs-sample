package com.rites.sample.ssa.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.MediaType;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import com.rites.sample.ssa.model.ConnectionInfo;

@Controller
@RequestMapping(value = "/api/connect")
public class RestConnectController /* implements InitializingBean */ {
	
	private final ConnectionFactoryLocator connectionFactoryLocator;
	private final ConnectionRepository connectionRepository;
	private ConnectSupport connectSupport;
	
	private final MultiValueMap<Class<?>, ConnectInterceptor<?>> connectInterceptors = new LinkedMultiValueMap<Class<?>, ConnectInterceptor<?>>();
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestConnectController.class);
	
	@Inject
	public RestConnectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.connectionRepository = connectionRepository;
	}
	
	@RequestMapping(produces={MediaType.APPLICATION_JSON_VALUE}, method=RequestMethod.GET)
	@ResponseBody
	public Set<ConnectionInfo> connect() {
		final Set<ConnectionInfo> response = new HashSet<>();
		final Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
		final Map<String, List<Connection<?>>> connections = connectionRepository.findAllConnections();
		if (CollectionUtils.isNotEmpty(registeredProviderIds)) {
			for (String providerId : registeredProviderIds) {
				ConnectionInfo connectionInfo = new ConnectionInfo(providerId, "Alias");
				List<Connection<?>> connectionList = connections.get(providerId);
				if (CollectionUtils.isNotEmpty(connectionList)) {
					String displayName = connectionList.get(0).getDisplayName();
					if (StringUtils.isNotBlank(displayName)) {
						connectionInfo.setDisplayName(displayName);
					}
					connectionInfo.markAsConfigured();
				}
				
				response.add(connectionInfo);
			}
		}
		return response;
	}
//	
//	/**
//	 * Process a connect form submission by commencing the process of establishing a connection to the provider on behalf of the member.
//	 * For OAuth1, fetches a new request token from the provider, temporarily stores it in the session, then redirects the member to the provider's site for authorization.
//	 * For OAuth2, redirects the user to the provider's site for authorization.
//	 */
//	@RequestMapping(value="/{providerId}", method=RequestMethod.POST)
//	public RedirectView connect(@PathVariable String providerId, NativeWebRequest request) {
//		ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
//		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>(); 
//		preConnect(connectionFactory, parameters, request);
//		try {
//			return new RedirectView(connectSupport.buildOAuthUrl(connectionFactory, request, parameters));
//		} catch (Exception e) {
//			sessionStrategy.setAttribute(request, PROVIDER_ERROR_ATTRIBUTE, e);
////			return connectionStatusRedirect(providerId, request);
//			return null;
//		}
//	}
//	
//	@RequestMapping(value="/{providerId}", method=RequestMethod.GET, params="code")
//	public RedirectView oauth2Callback(@PathVariable String providerId, NativeWebRequest request) {
//		try {
//			OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
//			Connection<?> connection = connectSupport.completeConnection(connectionFactory, request);
//			addConnection(connection, connectionFactory, request);
//		} catch (Exception e) {
//			sessionStrategy.setAttribute(request, PROVIDER_ERROR_ATTRIBUTE, e);
//			LOGGER.warn("Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + providerId +" connection status page.");
//		}
//		//return connectionStatusRedirect(providerId, request);
//		return new RedirectView("/ssa/index.html#/connections");
//	}
//
//	private void addConnection(Connection<?> connection, ConnectionFactory<?> connectionFactory, WebRequest request) {
//		try {
//			connectionRepository.addConnection(connection);
//			postConnect(connectionFactory, connection, request);
//		} catch (DuplicateConnectionException e) {
//			sessionStrategy.setAttribute(request, DUPLICATE_CONNECTION_ATTRIBUTE, e);
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void preConnect(ConnectionFactory<?> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
//		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
//			interceptor.preConnect(connectionFactory, parameters, request);
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void postConnect(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
//		for (ConnectInterceptor interceptor : interceptingConnectionsTo(connectionFactory)) {
//			interceptor.postConnect(connection, request);
//		}
//	}
//	
//	private List<ConnectInterceptor<?>> interceptingConnectionsTo(ConnectionFactory<?> connectionFactory) {
//		Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
//		List<ConnectInterceptor<?>> typedInterceptors = connectInterceptors.get(serviceType);
//		if (typedInterceptors == null) {
//			typedInterceptors = Collections.emptyList();
//		}
//		return typedInterceptors;
//	}
//	
//	/**
//	 * Configure the list of connect interceptors that should receive callbacks during the connection process.
//	 * Convenient when an instance of this class is configured using a tool that supports JavaBeans-based configuration.
//	 * @param interceptors the connect interceptors to add
//	 */
//	public void setConnectInterceptors(List<ConnectInterceptor<?>> interceptors) {
//		for (ConnectInterceptor<?> interceptor : interceptors) {
//			addInterceptor(interceptor);
//		}
//	}
//	
//	/**
//	 * Adds a ConnectInterceptor to receive callbacks during the connection process.
//	 * Useful for programmatic configuration.
//	 * @param interceptor the connect interceptor to add
//	 */
//	public void addInterceptor(ConnectInterceptor<?> interceptor) {
//		Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), ConnectInterceptor.class);
//		connectInterceptors.add(serviceApiType, interceptor);
//	}
//	
//	@Override
//	public void afterPropertiesSet() throws Exception {
//		this.connectSupport = new ConnectSupport(sessionStrategy);
//	}
//	
//	private static final String DUPLICATE_CONNECTION_ATTRIBUTE = "social_addConnection_duplicate";
//	private static final String PROVIDER_ERROR_ATTRIBUTE = "social_provider_error";
}
