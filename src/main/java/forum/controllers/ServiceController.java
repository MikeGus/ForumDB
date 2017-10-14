package forum.controllers;

import forum.models.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by MikeGus on 14.10.17
 */

@RequestMapping("api/service")
public class ServiceController {

    @RequestMapping(value = "clear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clearDB() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> getStatusDB() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
