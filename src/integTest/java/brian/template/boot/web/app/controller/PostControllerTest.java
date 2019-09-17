package brian.template.boot.web.app.controller;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.IfProfileValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IfProfileValue(name = "spring.profiles.active", values = { "default", "ist,ist-azr-use2", "ist,ist-azr-cc" })
public class PostControllerTest {

    @Test
    public void testController_withNoParams_shouldReturnAllList(){

    }
}
