package com.iit.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import com.iit.security.ApplicationUser;

@Service
public class JwtService {

    private static final String SECRET_KEY = "MySuperSecretKeyForJWTGeneration1234567890";

 public String generateToken(ApplicationUser user) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    Long realId = null;
    if(user.getRole() == ApplicationUser.Role.FORMATEUR && user.getFormateur() != null) {
        realId = user.getFormateur().getId();
    } else if(user.getRole() == ApplicationUser.Role.ETUDIANT && user.getEtudiant() != null) {
        realId = user.getEtudiant().getId();
    }

    return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("role", user.getRole().name())
            .claim("realId", realId) 
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
            .signWith(key)
            .compact();
}


    public Claims extractClaims(String token){
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


 // Génère un JWT avec l'id spécifique selon le rôle
// public String generateRoleBasedToken(long expiration, String secretKey) {
//     Long realId = null; // <- ID réel du formateur ou étudiant
//     if (getRole() == Role.FORMATEUR && getFormateur() != null) {
//         realId = getFormateur().getId();
//     } else if (getRole() == Role.ETUDIANT && getEtudiant() != null) {
//         realId = getEtudiant().getId();
//     }

//     return io.jsonwebtoken.Jwts.builder()
//         .setSubject(getEmail())
//         .claim("role", getRole().name())
//         .claim("realId", realId) // <-- ici
//         .setIssuedAt(new java.util.Date())
//         .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
//         .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
//         .compact();
// }

}