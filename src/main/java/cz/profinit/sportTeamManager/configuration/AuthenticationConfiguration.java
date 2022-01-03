    /*
     * AuthenticationConfiguration
     *
     * 0.1
     *
     * Author: J. Jansky
     */

    package cz.profinit.sportTeamManager.configuration;

    import cz.profinit.sportTeamManager.oauth.PrincipalExtractorImpl;
    import cz.profinit.sportTeamManager.repositories.user.UserRepository;
    import cz.profinit.sportTeamManager.service.user.AuthenticationFacade;
    import cz.profinit.sportTeamManager.service.user.AuthenticationFacadeImpl;
    import cz.profinit.sportTeamManager.service.user.UserDetailServiceImpl;
    import cz.profinit.sportTeamManager.service.user.UserDetailsImpl;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.annotation.*;
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.password.PasswordEncoder;

    /**
     * Configuration bringing classes necessary for Authentication.
     */
    @Configuration
    @Import(PasswordEncoderBean.class)
    @ComponentScan(basePackageClasses = AuthenticationFacade.class)
    @Profile("Main")
    public class AuthenticationConfiguration {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ApplicationContext context;


        /**
         * Initialize Authentication provider.
         */
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            UserDetailsService userDetailsService = new UserDetailServiceImpl(userRepository);
            UserDetails userDetails = new UserDetailsImpl();
            PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return authenticationProvider;
        }


        @Bean AuthenticationFacade authenticationFacade() {
            return new AuthenticationFacadeImpl();
        }

        @Bean
        PrincipalExtractorImpl principalExtractor() {
            return new PrincipalExtractorImpl();
        }
    }

