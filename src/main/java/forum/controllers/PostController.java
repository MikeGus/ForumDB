package forum.controllers;

import forum.models.ErrorModel;
import forum.models.PostFullModel;
import forum.models.PostModel;
import forum.models.PostUpdateModel;
import forum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by MikeGus on 12.10.17
 */

@SuppressWarnings({"unused", "SpringAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping("api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "{id}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPostDetails(@PathVariable(value = "id") final Integer id,
                                                    @RequestParam(value = "related", required = false) final List<String> related) {
        try {
            PostFullModel result = postService.getByIdFull(id, related);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }

    @RequestMapping(value = "{id}/details", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePostDetails(@PathVariable(value="id") final Integer id,
                                                       @RequestBody final PostUpdateModel post) {
        try {
            PostModel result = postService.update(id, post);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (DataAccessException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorModel(ex.getMessage()));
        }
    }
}
