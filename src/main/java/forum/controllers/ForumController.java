package forum.controllers;

import forum.models.Forum;
import forum.models.Thread;
import forum.models.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MikeGus on 12.10.17
 */

@RestController
@RequestMapping(value="api/forum")
public class ForumController {

    @RequestMapping(value="create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Forum> createForum(@RequestBody final Forum forum) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thread> createThread(@RequestBody final Thread thread,
                                               @PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Forum> getForumDetails(@PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug}/threads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Thread>> getThreads(@PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers(@PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
