package com.openclassrooms.mddapi.serviceInterface;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Interface de gestion des jetons JWT.
 */
public interface JwtInterface {
	/**
	* Génère un jeton JWT pour un identifiant utilisateur.
	*
	* @param id : l'identifiant de l'utilisateur.
	* @return : le jeton JWT généré.
	*/
  String generateToken(long id);
  /**
  * Décode un jeton JWT.
  *
  * @param token Le jeton JWT à décoder.
  * @return Le JWT décodé.
  */
  Jwt decodeToken(String token);

  /**
  * Extrait l'identifiant utilisateur d'un JWT.
  *
  * @param jwt Le JWT duquel extraire l'identifiant utilisateur.
  * @return L'identifiant utilisateur extrait du JWT.
  */
  long getUserIdFromJwtLong(Jwt jwt);

  /**
  * Vérifie si les identifiants utilisateur extraits d'un JWT et de la charge utile de la requête correspondent.
  *
  * @param userIdFromToken : identifiant utilisateur extrait du JWT.
  * @param userIdFromRequestPayload : identifiant utilisateur de la charge utile de la requête.
  * @return : vrai si les identifiants utilisateur correspondent, faux dans le cas contraire.
  */
  boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  );
}