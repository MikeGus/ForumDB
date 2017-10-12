package forum.controllers;

import forum.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by MikeGus on 12.10.17
 */

@RequestMapping("api/post")
public class PostController {

    @RequestMapping(value = "{id}/details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Post> getPostDetails(@PathVariable(value = "id") final Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
