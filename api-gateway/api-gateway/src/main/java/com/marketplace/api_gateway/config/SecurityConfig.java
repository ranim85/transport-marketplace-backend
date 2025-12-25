@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.csrf().disable()
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/auth/**").permitAll()
                .anyExchange().authenticated()
            )
                //JWT
                .httpBasic().disable()
                .formLogin().disable();
        return http.build();
    }
}