package forum.controllers;

import forum.models.Post;
import forum.models.Thread;
import forum.models.ThreadUpdate;
import forum.models.Vote;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MikeGus on 14.10.17
 */

@RestController
@RequestMapping("api/thread")
public class ThreadController {

    @RequestMapping(value = "{slug_or_id}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> createPosts(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                  @RequestBody Post[] posts) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thread> getThread(@PathVariable(value = "slug_or_id") final String slug_or_id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thread> updateThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                               @RequestBody final ThreadUpdate threadUpdate) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/posts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Post>> getPostsSorted(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                     @RequestParam(value = "limit") final Integer limit,
                                                     @RequestParam(value = "since") final Integer since,
                                                     @RequestParam(value = "sort") final String sort,
                                                     @RequestParam(value = "desc") final Boolean desc) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/vote", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Thread> voteThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                             @RequestBody final Vote vote) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
