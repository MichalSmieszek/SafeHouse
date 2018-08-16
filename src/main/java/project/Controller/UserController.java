package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.User;
import project.Repository.UserRepository;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @GetMapping(path="/add")
    @ResponseBody
    public String addUser (@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam char sex){
        if (sex=='f' || sex=='m') {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setSex(sex);
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
    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable int id){
        User user = userRepository.findById(id);
        userRepository.delete(user);
        return("User deleted");
    }


}
