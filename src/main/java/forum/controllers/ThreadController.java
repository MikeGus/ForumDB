package forum.controllers;

import forum.models.*;
import forum.services.PostService;
import forum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by MikeGus on 14.10.17
 */

@SuppressWarnings({"unused", "SpringAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping("api/thread")
public class ThreadController {

    @Autowired
    private ThreadService threadService;

    @Autowired
    private PostService postService;

    @RequestMapping(value = "{slug_or_id}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPosts(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                       @RequestBody List<PostModel> posts) {
        try {
            List<PostModel> result = postService.create(slug_or_id, posts);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IncorrectResultSizeDataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getThread(@PathVariable(value = "slug_or_id") final String slug_or_id) {
        try {
            ThreadModel result = threadService.getBySlugOrId(slug_or_id);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }

    }

    @RequestMapping(value = "{slug_or_id}/details", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                    @RequestBody final ThreadUpdateModel threadUpdate) {
        try {
            ThreadModel result = threadService.update(slug_or_id, threadUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug_or_id}/posts", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostsSorted(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                          @RequestParam(value = "limit", required = false) final Integer limit,
                                                          @RequestParam(value = "since", required = false) final Integer since,
                                                          @RequestParam(value = "sort", required = false) final String sort,
                                                          @RequestParam(value = "desc", required = false) final Boolean desc) {
        try {
            List<PostModel> result = threadService.getPosts(slug_or_id, limit, since, sort, desc);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug_or_id}/vote", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity voteThread(@PathVariable(value = "slug_or_id") final String slug_or_id,
                                                  @RequestBody final VoteModel vote) {
        try {
            ThreadModel result = threadService.vote(slug_or_id, vote);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorModel(ex.getMessage()));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }
}
