package com.mushr00m.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
	private static final String SECRET = "!@#123abcxyz";
	private static final String EXP = "exp";
	private static final String PAYLOAD = "payload";

	public static <T> String getToken(T object, long maxAge) {
		try {
			final JWTSigner signer = new JWTSigner(SECRET);
			final Map<String, Object> claims = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(object);
			claims.put(PAYLOAD, jsonString);
			claims.put(EXP, System.currentTimeMillis() + maxAge);
			return signer.sign(claims);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getObject(String token, Class<T> classT) {
		final JWTVerifier verifier = new JWTVerifier(SECRET);
		try {
			final Map<String, Object> claims = verifier.verify(token);
			if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
				long exp = (Long) claims.get(EXP);
				long currentTimeMillis = System.currentTimeMillis();
				if (exp > currentTimeMillis) {
					String json = (String) claims.get(PAYLOAD);
					ObjectMapper objectMapper = new ObjectMapper();
					return objectMapper.readValue(json, classT);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}