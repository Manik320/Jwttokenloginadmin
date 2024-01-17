package com.userstory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userstory.dao.RoleDao;
import com.userstory.dao.UserDao;
import com.userstory.entity.Role;
import com.userstory.entity.User;
import com.userstory.repository.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

//        User adminUser = new User();
//        adminUser.setUserName("admin123");
//        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
//        adminUser.setUserFirstName("admin");
//        adminUser.setUserLastName("admin");
//        Set<Role> adminRoles = new HashSet<>();
//        adminRoles.add(adminRole);
//        adminUser.setRole(adminRoles);
//        userDao.save(adminUser);

//        User user = new User();
//        user.setUserName("raj123");
//        user.setUserPassword(getEncodedPassword("raj@123"));
//        user.setUserFirstName("raj");
//        user.setUserLastName("sharma");
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        user.setRole(userRoles);
//        userDao.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
        
        
    }
    public User registerNewAdmin(User admin) {
        Role role = roleDao.findById("Admin").get();
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(role);
        admin.setRole(adminRole);
        admin.setUserPassword(getEncodedPassword(admin.getUserPassword()));

        return userDao.save(admin);
        
        
    }
    
    public Optional<User> getUsers() {
        return userRepository.findByRoleRoleName("User");
    }
    
    public Optional<User> getAdmin() {
        return userRepository.findByRoleRoleName("Admin");
    }
    
    public Optional<User> deleteUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findById(username);

        if (userOptional.isPresent()) {
            userRepository.deleteById(username);
        }

        return userOptional;
    }
    public Optional<User> updateUser(User updatedUser) {
        String username = updatedUser.getUserName();
        Optional<User> existingUserOptional = userRepository.findById(username);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update user details
            existingUser.setUserFirstName(updatedUser.getUserFirstName());
            existingUser.setUserLastName(updatedUser.getUserLastName());
            // Update other fields as needed

            // Save the updated user
            return Optional.of(userRepository.save(existingUser));
        }

        return Optional.empty(); // User not found
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
