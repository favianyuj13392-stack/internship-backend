package ucb.edu.bo.internship.internship_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ucb.edu.bo.internship.internship_backend.bl.UsuariosBL;
import ucb.edu.bo.internship.internship_backend.dto.UsuariosDto;

import java.io.IOException;
import java.util.Collection;

public class RegistrationCompletionFilter extends OncePerRequestFilter{

    private String clientUrl;
    private final UsuariosBL usuariosBL;
    private final KeycloakJwtTokenConverter keycloakJwtTokenConverter;

    private Logger logger = LoggerFactory.getLogger(RegistrationCompletionFilter.class);

    public RegistrationCompletionFilter (KeycloakJwtTokenConverter keycloakJwtTokenConverter, UsuariosBL usuariosBL, String clientUrl){
        this.keycloakJwtTokenConverter = keycloakJwtTokenConverter;
        this.usuariosBL = usuariosBL;
        this.clientUrl = clientUrl;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws
            ServletException,
            IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt){
            Jwt jwtToken = (Jwt) authentication.getPrincipal();
            String sub = jwtToken.getSubject();

            AbstractAuthenticationToken token = keycloakJwtTokenConverter.convert(jwtToken);
            Collection<GrantedAuthority> authorities = token.getAuthorities();
            if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                filterChain.doFilter(request, response);
                return;
            }
            UsuariosDto user = usuariosBL.obtenerUsuario(sub);
            if (user == null){
                response.sendRedirect(clientUrl + "/complete-registration");
                logger.info("Usuario no encontrado, redirigiendo a la página de completar registro " + clientUrl + "/complete-registration");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}
