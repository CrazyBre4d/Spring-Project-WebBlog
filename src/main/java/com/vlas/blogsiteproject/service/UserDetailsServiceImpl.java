package com.vlas.blogsiteproject.service;

import com.vlas.blogsiteproject.dao.UserDetailRepository;
import com.vlas.blogsiteproject.entities.User;
import com.vlas.blogsiteproject.entities.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public void saveUserDetail(UserDetail userDetail) {
        userDetailRepository.save(userDetail);
    }

    @Override
    public List<UserDetail> getAllUsers() {
        return userDetailRepository.findAll();
    }

    @Override
    public void deleteUserDetail(Long id) {
        userDetailRepository.deleteById(id);
    }

    @Override
    public UserDetail getUserDetail(Long id) {
        UserDetail userDetail = null;
        Optional<UserDetail> optional = userDetailRepository.findById(id);
        if(optional.isPresent()){
            userDetail = optional.get();
        }
        return userDetail;
    }
}
