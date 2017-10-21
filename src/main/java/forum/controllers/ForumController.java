package forum.controllers;

import forum.services.ForumService;
import forum.models.ErrorModel;
import forum.models.ForumModel;
import forum.models.ThreadModel;
import forum.models.UserModel;

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

@SuppressWarnings("unused")
@RestController
@RequestMapping(value="api/forum")
public class ForumController {

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private ForumService jdbcForum;

    @RequestMapping(value="create", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createForum(@RequestBody final ForumModel forum) {
        try {
            jdbcForum.create(forum);
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(jdbcForum.getBySlug(forum.getSlug()));
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel("Owner not found"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(jdbcForum.getBySlug(forum.getSlug()));
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
