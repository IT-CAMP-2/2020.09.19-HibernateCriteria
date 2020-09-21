package pl.camp.it.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.camp.it.model.User;
import pl.camp.it.service.IUserService;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/addUsers", method = RequestMethod.GET)
    public ResponseEntity addUsers() {
        this.userService.addUser(new User(1, "mateusz", "mateusz"));
        this.userService.addUser(new User(1, "janusz", "janusz"));
        this.userService.addUser(new User(1, "maciej", "maciej"));
        this.userService.addUser(new User(1, "karol", "karol"));

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) {
        return this.userService.getUserById(id);
    }

    @RequestMapping(value = "/getUserByIdCriteria/{id}", method = RequestMethod.GET)
    public User getUserByIdCriteria(@PathVariable int id) {
        return this.userService.getUserByIdCriteria(id);
    }

    @RequestMapping(value = "/getUserByCriteria", method = RequestMethod.GET)
    public List<User> getUserByCriteria() {
        return userService.getUserByCriteria();
    }

}
