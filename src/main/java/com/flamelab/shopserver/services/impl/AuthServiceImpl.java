package com.flamelab.shopserver.services.impl;

import com.flamelab.shopserver.dtos.update.UpdateUserAuthToken;
import com.flamelab.shopserver.entities.AuthToken;
import com.flamelab.shopserver.entities.User;
import com.flamelab.shopserver.enums.AuthTokenType;
import com.flamelab.shopserver.enums.Roles;
import com.flamelab.shopserver.exceptions.ResourceException;
import com.flamelab.shopserver.dtos.create.internal.InternalCreateUserAuthToken;
import com.flamelab.shopserver.services.AuthService;
import com.flamelab.shopserver.utiles.DbEntityUtility;
import com.flamelab.shopserver.utiles.MapperUtility;
import com.flamelab.shopserver.utiles.naming.FieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.flamelab.shopserver.enums.AuthTokenType.BEARER;
import static com.flamelab.shopserver.utiles.naming.DbCollectionNames.AUTH__DB_COLLECTION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.EMAIL__FIELD_APPELLATION;
import static com.flamelab.shopserver.utiles.naming.FieldNames.TOKEN__FIELD_APPELLATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final int MAX_USAGES_AMOUNT = 5;
    private final AuthTokenType tokenType = BEARER;
    private final DbEntityUtility<AuthToken, InternalCreateUserAuthToken, UpdateUserAuthToken> dbEntityUtility;
    private final MapperUtility<AuthToken, UpdateUserAuthToken> mapperFromEntityToUpdateDto;

    @Override
    public boolean isPasswordCorrect(User user, String enteredPassword) {
        return user.getPassword().equals(enteredPassword);
    }

    @Override
    public AuthToken createToken(InternalCreateUserAuthToken createUserAuthToken, User user) {
        return dbEntityUtility.saveEntity(createUserAuthToken, InternalCreateUserAuthToken.class, AuthToken.class, AUTH__DB_COLLECTION);
    }

    @Override
    public AuthToken getToken(Map<FieldNames, Object> criterias) {
        return dbEntityUtility.findOneBy(criterias, AuthToken.class, AUTH__DB_COLLECTION);
    }

    @Override
    public void increaseTokenUsageAmount(Map<FieldNames, Object> criterias) {
        AuthToken token = getToken(criterias);
        token.setUsageAmount(token.getUsageAmount() + 1);
        dbEntityUtility.updateEntity(
                criterias,
                mapperFromEntityToUpdateDto.map(token, AuthToken.class, UpdateUserAuthToken.class),
                UpdateUserAuthToken.class,
                AuthToken.class,
                AUTH__DB_COLLECTION
        );
    }

    @Override
    public boolean isTokenValid(String tokenValue, List<Roles> availableRoles) {
        if (tokenValue.startsWith(tokenType.getTypeName())) {
            tokenValue = tokenValue.replace(tokenType.getTypeName() + " ", "");
        } else {
            throw new ResourceException(UNAUTHORIZED, "Wrong token provided");
        }
        AuthToken token = getToken(Map.of(TOKEN__FIELD_APPELLATION, tokenValue));
        return token.getUsageAmount() < MAX_USAGES_AMOUNT && availableRoles.contains(token.getRole());
    }

    @Override
    public boolean isTokenExists(String email) {
        return dbEntityUtility.isEntityExistsBy(Map.of(EMAIL__FIELD_APPELLATION, email), AuthToken.class, AUTH__DB_COLLECTION);
    }

    @Override
    public void deleteTokenByValue(String tokenValue) {
        dbEntityUtility.deleteEntityBy(Map.of(TOKEN__FIELD_APPELLATION, tokenValue), AuthToken.class, AUTH__DB_COLLECTION);
    }

    @Override
    public void deleteTokenByEmail(String email) {
        dbEntityUtility.deleteEntityBy(Map.of(EMAIL__FIELD_APPELLATION, email), AuthToken.class, AUTH__DB_COLLECTION);
    }

}
