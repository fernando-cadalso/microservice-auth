package br.com.alura.microservice.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

	/*
	 * Injetar as dependências expostas pela classe WebSecurityConfigurer
	 */
	@Autowired
	private AuthenticationManager athenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * Implementar o método que efetiva o relacionamento entre o Spring Security
	 * (classe WebSecurityConfigurer)e o OAuth2 (esta classe)
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		/*
		 * Passar as injeções de dependências como parâmetros de autenticação e de detalhes
		 * do usuário para o método.
		 */
		endpoints.authenticationManager(athenticationManager).userDetailsService(userDetailsService);
	}
	/*
	 * Implementar um método que faça o registro da aplicação loja para interações com o usuário.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		/*
		 * Cria o registro em memória da aplicação com método .inMemory()
		 * Encadeia com a identificação do cliente, com o método .withClient()
		 * E encadeia com a identificação da senha, com o método .secret().
		 * Foi definido o tipo de acesso dado para aplicação, através do método .authorizedGrantTypes().
		 * Usei a injeção de dependência "passwordEncoder" para criptografar a senha.
		 * Encadeei o método .scopes() para definir os perfis de acessos permitidos.
		 */
		clients.inMemory()
			.withClient("loja")
			.secret(passwordEncoder.encode("lojapwd"))
			.authorizedGrantTypes("password")
			.scopes("web","mobile");
	
		
	}
}
