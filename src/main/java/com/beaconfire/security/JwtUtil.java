package com.beaconfire.security;

import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.service.StudentService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${token.neverExpire}")
    private boolean tokenNeverExpire;


    @Autowired
    private StudentService studentService;

    // retrieve username from jwt token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Integer extractId(String token) {
        System.out.println("In ExtractID: " + extractClaim(token, claims -> claims.get("id", Integer.class)));
        return extractClaim(token, claims -> claims.get("id", Integer.class));
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {

        if(tokenNeverExpire){
            return false;
        }else{
            return extractExpiration(token).before(new Date());
        }
    }

    // generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        StudentHibernate student = studentService.getStudentByEmail2(userDetails.getUsername());
//        claims.put("is_admin", student.getIs_admin()); don't need to put is_admin field in jwt token
        claims.put("id", student.getId());
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secret);

        if (!tokenNeverExpire) {//if token do expire
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10 )); // Set to 10 hours if tokenNeverExpire is false
        }

        return jwtBuilder.compact();
    }


    // validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

