package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.mappers.AuthTokenMapper;
import com.flamelab.shopserver.repositories.AuthorizationRepository;
import com.flamelab.shopserver.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthorizationRepository authorizationRepository;
    private final AuthTokenMapper authTokenMapper;

    @Override
    public AuthToken createToken(User user) {
        return authorizationRepository.save(authTokenMapper.generateAuthToken(user));
    }

    @Override
    public AuthToken validateTokenAndReturn(String token, List<Roles> availableRoles) {
        if (token == null || token.isEmpty() || !token.startsWith(BEARER.getTypeName())) {
            throw new ResourceException(UNAUTHORIZED, "Unauthorized");
        } else {
            token = token.replace(BEARER.getTypeName() + " ", "");
            Optional<AuthToken> optionalAuthToken = authorizationRepository.findByToken(token);
            if (optionalAuthToken.isPresent()) {
                increaseTokenUsageAmount(optionalAuthToken.get());
                return optionalAuthToken.get();
            } else {
                throw new ResourceException(UNAUTHORIZED, "UNAUTHORIZED");
            }
        }
    }

    private void increaseTokenUsageAmount(AuthToken token) {
        token.setUsageAmount(token.getUsageAmount() + 1);
        authorizationRepository.save(token);
    }

    @Override
    public boolean isTokenExists(String email) {
        return authorizationRepository.existsByEmail(email);
    }

    @Override
    public void deleteTokenByValue(String token) {
        authorizationRepository.deleteByToken(token);
    }

    @Override
    public void deleteTokenByEmail(String email) {
        authorizationRepository.deleteByEmail(email);
    }
}
