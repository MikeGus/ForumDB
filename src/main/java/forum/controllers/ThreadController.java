package forum.controllers;

import forum.models.PostModel;
import forum.models.ThreadModel;
import forum.models.ThreadUpdateModel;
import forum.models.VoteModel;
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
    public ResponseEntity<List<PostModel>> createPosts(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                       @RequestBody PostModel[] posts) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadModel> getThread(@PathVariable(value = "slug_or_id") final String slug_or_id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadModel> updateThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                    @RequestBody final ThreadUpdateModel threadUpdate) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/posts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PostModel>> getPostsSorted(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                          @RequestParam(value = "limit") final Integer limit,
                                                          @RequestParam(value = "since") final Integer since,
                                                          @RequestParam(value = "sort") final String sort,
                                                          @RequestParam(value = "desc") final Boolean desc) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{slug_or_id}/vote", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadModel> voteThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                  @RequestBody final VoteModel vote) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
