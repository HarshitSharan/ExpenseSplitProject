package org.hsharan.userAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class UserSerivce {

    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public void addUser(UserDetails userDetails) throws ValidationException {
        if(findUserById(userDetails.getId())!=null){
            throw new ValidationException("User Id already exist");
        }
        UserValidations.validateUser(userDetails);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetailsRepository.save(userDetails);
    }

    public UserDetails findUserById(String userId){
        return userDetailsRepository.findById(userId).orElse(null);
    }

    public String getUserIdFromBasicAuth(String authHeader) {
        String base64Credentials = authHeader.substring("Basic".length()).trim();
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        return new String(decodedBytes, StandardCharsets.UTF_8).split(":")[0];
    }
}

