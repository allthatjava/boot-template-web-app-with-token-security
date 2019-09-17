package brian.template.boot.web.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import brian.template.boot.web.app.domain.AppPost;
import brian.template.boot.web.app.repository.AppPostRepository;
import brian.template.boot.web.app.service.AppPostService;

@RunWith(MockitoJUnitRunner.class)
public class AppPostServiceTest {

	@Mock private AppPostRepository postRepository;
    @InjectMocks private AppPostService service;

    private List<AppPost> expectedList;

    @Before
    public void setUp(){
//        service = new PostService();	// @InjectMocks will initialize it.

        expectedList = new ArrayList<>();
        AppPost p1 = new AppPost();
        p1.setSubject("test");
        expectedList.add(p1);
    }

    @Test
    public void testGetAllPost_shouldReturnList(){

        // Given
        List<AppPost> list = new ArrayList<>();
        AppPost p1 = new AppPost();
        p1.setSubject("test");
        list.add(p1);

        // When
        when(postRepository.findAll()).thenReturn(expectedList);

        // Test
        List<AppPost> expected = service.getAllPost();

        assertThat(list.get(0).getSubject()).isEqualTo(expected.get(0).getSubject());
    }

}
