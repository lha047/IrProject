package uib.info323.twitterAWSM.model;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SocialConfig {

	// @Inject
	// private Environment environment;
	//
	// // @Inject
	// // private DataSource dataSource;
	//
	// @Bean
	// public ConnectionFactoryLocator connectionFactoryLocator() {
	// ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	// // registry.addConnectionFactory(new
	// //
	// FacebookConnectionFactory(environment.getProperty("facebook.clientId"),
	// // environment.getProperty("facebook.clientSecret")));
	// registry.addConnectionFactory(new TwitterConnectionFactory(environment
	// .getProperty("twitter.consumerKey"), environment
	// .getProperty("twitter.consumerSecret")));
	// return registry;
	// }
	// @Bean
	// public UsersConnectionRepository usersConnectionRepository() {
	// JdbcUsersConnectionRepository repository = new
	// JdbcUsersConnectionRepository(dataSource,
	// connectionFactoryLocator(), Encryptors.noOpText());
	// repository.setConnectionSignUp(new SimpleConnectionSignUp());
	// return repository;
	// }
	//
	// @Bean
	// @Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	// public ConnectionRepository connectionRepository() {
	// User user = SecurityContext.getCurrentUser();
	// return
	// usersConnectionRepository().createConnectionRepository(user.getId());
	// }

}
