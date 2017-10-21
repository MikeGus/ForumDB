package forum.controllers;

import forum.jdbc.JdbcForum;
import forum.jdbc.JdbcPost;
import forum.jdbc.JdbcThread;
import forum.jdbc.JdbcUser;
import forum.models.StatusModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MikeGus on 14.10.17
 */

@RestController
@RequestMapping("api/service")
public class ServiceController {

    private JdbcUser jdbcUser;
    private JdbcForum jdbcForum;
    private JdbcPost jdbcPost;
    private JdbcThread jdbcThread;

    @RequestMapping(value = "clear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> clearDB() {
        jdbcUser.clear();
        jdbcForum.clear();
        jdbcPost.clear();
        jdbcThread.clear();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusModel> getStatusDB() {

        Integer userNumber = jdbcUser.status();
        Integer forumNumber = jdbcForum.status();
        Integer postNumber = jdbcPost.status();
        Integer threadNumber = jdbcThread.status();

        return ResponseEntity.status(HttpStatus.OK).body(new StatusModel(forumNumber, postNumber,
                threadNumber, userNumber));
    }
}
