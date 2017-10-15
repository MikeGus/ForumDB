package forum.controllers;

import forum.models.PostModel;
import forum.models.PostUpdateModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by MikeGus on 12.10.17
 */

@RestController
@RequestMapping("api/post")
public class PostController {

    @RequestMapping(value = "{id}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostModel> getPostDetails(@PathVariable(value = "id") final Integer id,
                                                    @RequestParam(value = "related") final String[] related) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "{id}/details", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostModel> updatePostDetails(@PathVariable(value="id") final Integer id,
                                                       @RequestBody final PostUpdateModel post) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
