package forum.controllers;

import forum.models.StatusModel;
import forum.services.ForumService;
import forum.services.PostService;
import forum.services.ThreadService;
import forum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
@RestController
@RequestMapping("api/service")
public class ServiceController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private PostService postService;

    @Autowired
    private ThreadService threadService;

    @RequestMapping(value = "clear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clearDB() {
        userService.clear();
        forumService.clear();
        postService.clear();
        threadService.clear();
        forumService.clearVisitors();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusModel> getStatusDB() {

        Integer userNumber = userService.status();
        Integer forumNumber = forumService.status();
        Integer postNumber = postService.status();
        Integer threadNumber = threadService.status();

        return ResponseEntity.status(HttpStatus.OK).body(new StatusModel(forumNumber, postNumber,
                threadNumber, userNumber));
    }
}
