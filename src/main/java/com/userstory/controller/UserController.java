package com.userstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.userstory.entity.User;
import com.userstory.service.UserService;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRoleAndUser() {
        userService.initRoleAndUser();
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user) {
        return userService.registerNewUser(user);
    }
    @PostMapping({"/registerNewAdmin"})
    public User registerNewAdmin(@RequestBody User admin) {
        return userService.registerNewAdmin(admin);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public Optional<User> getUsersForAdmin() {
        return userService.getAdmin();
    }

          
   @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public Optional<User> getUsersForUser() {
        return userService.getUsers();
     }
    
   @DeleteMapping("/deleteUser/{username}")
   public String deleteUser(@PathVariable String username) {
       Optional<User> deletedUser = userService.deleteUserByUsername(username);

       return deletedUser.map(user -> "User " + username + " deleted successfully.")
               .orElse("User with username " + username + " not found.");
   }
   
   @PutMapping("/updateUser")
   public String updateUser(@RequestBody User updatedUser) {
       Optional<User> updatedUserOptional = userService.updateUser(updatedUser);

       return updatedUserOptional.map(user -> "User " + user.getUserName() + " updated successfully.")
               .orElse("User not found.");
   }
   
}
