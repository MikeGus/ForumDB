package forum.controllers;

import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;

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
    public ResponseEntity<ForumModel> createForum(@RequestBody final ForumModel forum) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadModel> createThread(@RequestBody final ThreadModel thread,
                                                    @PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForumModel> getForumDetails(@PathVariable(value = "slug") final String slug) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug}/threads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThreadModel>> getThreads(@PathVariable(value = "slug") final String slug,
                                                        @RequestParam(value = "limit") final Integer limit,
                                                        @RequestParam(value = "since") final String since,
                                                        @RequestParam(value = "desc") final Boolean desc) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserModel>> getUsers(@PathVariable(value = "slug") final String slug,
                                                    @RequestParam(value = "limit") final Integer limit,
                                                    @RequestParam(value = "since") final String since,
                                                    @RequestParam(value = "desc") final Boolean desc) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
