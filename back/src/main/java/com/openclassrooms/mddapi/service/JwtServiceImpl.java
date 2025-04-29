package com.openclassrooms.mddapi.service;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.serviceInterface.JwtInterface;

/**
 * Implementation de JwtInterface service.
 */
@Service
public class JwtServiceImpl implements JwtInterface {

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private JwtDecoder jwtDecoder;

  /**
  * Génère un jeton JWT.
  *
  * @param id L'identifiant de l'utilisateur pour lequel le jeton est généré.
  * @return Le jeton JWT généré.
  */
  @Override
  public String generateToken(long id) {
    String idToString = String.valueOf(id);
    Instant instantNow = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet
      .builder()
      .issuer("self")
      .issuedAt(instantNow)
      .expiresAt(instantNow.plus(1, ChronoUnit.DAYS))
      .subject(idToString)
      .build();

    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
      JwsHeader.with(MacAlgorithm.HS256).build(),
      claims
    );
    return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }

  /**
  * Décode un jeton JWT.
  *
  * @param token Le jeton JWT à décoder.
  * @return Le JWT décodé.
  */
  @Override
  public Jwt decodeToken(String token) {
    return jwtDecoder.decode(token);
  }

  /**
  * Extrait l'identifiant utilisateur d'un JWT.
  *
  * @param jwt Le JWT duquel extraire l'identifiant utilisateur.
  * @return L'identifiant utilisateur extrait du JWT.
  */
  @Override
  public long getUserIdFromJwtLong(Jwt jwt) {
    return Long.parseLong(jwt.getSubject());
  }

  /**
  * Vérifie si les identifiants utilisateur du jeton et de la charge utile de la requête correspondent.
  *
  * @param userIdFromToken : identifiant utilisateur extrait du jeton.
  * @param userIdFromRequestPayload : identifiant utilisateur de la charge utile de la requête.
  * @return : vrai si les identifiants utilisateur correspondent, faux sinon.
  */

  @Override
  public boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  ) {
    return userIdFromToken == userIdFromRequestPayload;
  }
}