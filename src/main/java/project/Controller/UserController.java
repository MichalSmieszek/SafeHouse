package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.User;
import project.Repository.UserRepository;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @CrossOrigin
    @GetMapping(path="/add")
    @ResponseBody
    public String addUser (@RequestParam String name,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam char sex){
        if (sex=='f' || sex=='m') {
            System.out.println("aaa");
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setSex(sex);
            user.setRole("User");
            userRepository.save(user);
            return (name);
        }
        else
            return ("Please use f or m to describe your sex");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
    public String updateUserEmail(@RequestBody User newUser) {
        try {
            User user = userRepository.findById(newUser.getId());
            user.setEmail(newUser.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed");
        }
        return("User's email updated");
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeSex", method = RequestMethod.POST)
    public String updateUserSex(@RequestBody User newUser) {
        try {
            User user = userRepository.findById(newUser.getId());
            if (newUser.getSex()=='m' || newUser.getSex()=='f') {
                user.setSex(newUser.getSex());
                userRepository.save(user);
            }
            else
               return ("Please use f or m to describe your sex");
        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed");
        }
        return("User's sex updated");
    }

    @CrossOrigin
    @ResponseBody
    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam User oldUser){
        User user = userRepository.findById(oldUser.getId());
        userRepository.delete(user);
        return("User deleted");
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/all")
    public List<User> showAllUser(){
        Iterable <User> users= userRepository.findAll();
        List <User> newValuesUsers = new LinkedList<>();
        User newUser =new User();
        for (User user : users){
            String email=user.getEmail();
            String name=user.getName();
            char sex=user.getSex();
            String role = user.getRole();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setSex(sex);
            newUser.setRole(role);
            newValuesUsers.add(newUser);
        }
       return (newValuesUsers);
    }


}
