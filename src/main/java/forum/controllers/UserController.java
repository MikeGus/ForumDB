package forum.controllers;

import forum.models.ErrorModel;
import forum.models.UserModel;
import forum.models.UserUpdateModel;
import forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "{nickname}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@PathVariable(value = "nickname") final String nickname,
                                                @RequestBody final UserModel user) {
        try {
            userService.create(nickname, user);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userService.getByNicknameOrEmail(nickname,
                    user.getEmail()));
        }

        user.setNickname(nickname);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable(value = "nickname") final String nickname) {
        UserModel user;
        try {
             user = userService.getByNickname(nickname);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value = "{nickname}/profile", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable(value = "nickname") final String nickname,
                                                @RequestBody final UserUpdateModel profile) {
        try {
            UserModel user = userService.update(nickname, profile);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorModel(ex.getMessage()));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }
}
