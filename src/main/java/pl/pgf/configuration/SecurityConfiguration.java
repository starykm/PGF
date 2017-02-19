package pl.pgf.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	public SecurityConfiguration() {
		super();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().
		withUser("Marek").password("demo").roles("ADMIN").and().
		withUser("Jurek").password("demo").roles("User");
		
	}
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll();
    }
	  @Override
	    public void configure(WebSecurity web) throws Exception {
	      
          web.ignoring().antMatchers("/css/**");
	      web.ignoring().antMatchers("/images/**");
	      web.ignoring().antMatchers("/js/**");
	    }

}