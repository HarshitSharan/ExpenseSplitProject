package org.hsharan.service;

import org.hsharan.entity.UserEntity;
import org.hsharan.repository.UserRepository;
import org.hsharan.userAuth.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public UserEntity convertAuthUserToUserEntity(UserDetails userDetails){
        UserEntity user = new UserEntity();
        user.setId(userDetails.getId());
        user.setEmail(userDetails.getEmail());
        user.setName(userDetails.getName());
        user.setPhoneNum(userDetails.getPhoneNum());
        return user;
    }

    public void addUser(UserEntity user){
        userRepository.save(user);
    }

    public UserEntity getUserById(String userId){
        return userRepository.findById(userId).orElse(null);
    }
}
