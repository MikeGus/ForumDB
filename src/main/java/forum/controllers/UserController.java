package forum.controllers;

import forum.models.User;
import forum.models.UserUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MikeGus on 14.10.17
 */

@RestController
@RequestMapping("api/user")
public class UserController {

    @RequestMapping(value = "{nickname}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@PathVariable(value = "nickname") final String nickname,
                                           @RequestBody final User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable(value = "nickname") final String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable(value = "nickname") final String nickname,
                                           @RequestBody final UserUpdate profile) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
