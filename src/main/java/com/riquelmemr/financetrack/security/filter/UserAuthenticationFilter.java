package com.riquelmemr.financetrack.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.riquelmemr.financetrack.security.userdetails.UserDetailsImpl;
import com.riquelmemr.financetrack.exception.ModelNotFoundException;
import com.riquelmemr.financetrack.model.UserModel;
import com.riquelmemr.financetrack.service.accesstoken.AccessTokenService;
import com.riquelmemr.financetrack.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoveryToken(request);

        if (nonNull(token)) {
            DecodedJWT decoded = accessTokenService.validateAndDecodedToken(token);
            String username = decoded.getSubject();

            UserModel userModel = userService.findByUsername(username);

            if (isNull(userModel)) {
                throw new ModelNotFoundException("User not found with " + username);
            }

            UserDetailsImpl userDetails = new UserDetailsImpl(userModel);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userModel, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (nonNull(authorizationHeader)) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
