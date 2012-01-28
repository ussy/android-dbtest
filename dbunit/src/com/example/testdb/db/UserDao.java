package com.example.testdb.db;

import com.example.testdb.model.User;

import java.util.List;

public interface UserDao {
    
    List<User> findAll();
    
    List<User> findByName(String name);
    
    void insert(User user);
    
    void update(User user);
    
    void delete(User user);
}
