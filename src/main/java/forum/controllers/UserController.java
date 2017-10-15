package forum.controllers;

import forum.models.UserModel;
import forum.models.UserUpdateModel;
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
    public ResponseEntity<UserModel> createUser(@PathVariable(value = "nickname") final String nickname,
                                                @RequestBody final UserModel user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> getUser(@PathVariable(value = "nickname") final String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> updateUser(@PathVariable(value = "nickname") final String nickname,
                                                @RequestBody final UserUpdateModel profile) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
