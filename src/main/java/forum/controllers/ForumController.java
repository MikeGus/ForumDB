package forum.controllers;

import forum.services.ForumService;
import forum.models.ErrorModel;
import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;

import forum.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MikeGus on 12.10.17
 */

@SuppressWarnings({"unused", "SpringAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping(value="api/forum")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ThreadService threadService;

    @RequestMapping(value="create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createForum(@RequestBody final ForumModel forum) {
        try {
            ForumModel result = forumService.create(forum);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(forumService.getBySlug(forum.getSlug()));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug}/create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createThread(@RequestBody final ThreadModel thread,
                                                    @PathVariable(value = "slug") final String slug) {
        try {
            ThreadModel result = threadService.create(slug, thread);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(threadService.getBySlugOrId(thread.getSlug()));
        }catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getForumDetails(@PathVariable(value = "slug") final String slug) {
        try {
            ForumModel forum = forumService.getBySlug(slug);
            return ResponseEntity.status(HttpStatus.OK).body(forum);
        } catch(DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel("Can't find forum with slug " + slug));
        }
    }

    @RequestMapping(value = "{slug}/threads", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getThreads(@PathVariable(value = "slug") final String slug,
                                                        @RequestParam(value = "limit", required = false) final Integer limit,
                                                        @RequestParam(value = "since", required = false) final String since,
                                                        @RequestParam(value = "desc", required = false) final Boolean desc) {
        try {
            ForumModel forum = forumService.getBySlug(slug);
            List<ThreadModel> threads = forumService.getThreads(slug, limit, since, desc);
            return ResponseEntity.status(HttpStatus.OK).body(threads);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{slug}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers(@PathVariable(value = "slug") final String slug,
                                                    @RequestParam(value = "limit", required = false) final Integer limit,
                                                    @RequestParam(value = "since", required = false) final String since,
                                                    @RequestParam(value = "desc", required = false) final Boolean desc) {
        try {
            List<UserModel> result = forumService.getUsers(slug, limit, since, desc);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }
}
