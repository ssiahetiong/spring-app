package tutorial.spring.awesome.admin.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import tutorial.spring.awesome.admin.config.AdminConfig;
import tutorial.spring.awesome.admin.dto.UserInfo;
import tutorial.spring.awesome.admin.entity.User;
import tutorial.spring.awesome.admin.exception.UserNotFoundException;
import tutorial.spring.awesome.admin.repo.UserRepo;
import tutorial.spring.awesome.admin.service.UserService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdminConfig.class}, loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Autowired
    @InjectMocks
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

/*    @Configuration
    static class RepoContextConfig {

        @Bean
        public UserRepo userRepo() {
            return new UserRepo() {
                @Override
                public void save(User user) {
                }

                @Override
                public User findByUsername(String username) {
                    return null;
                }
            };
        }

    }*/

    @Test
    public void testSaveUser() {
        UserInfo userInfo = new UserInfo();
        userService.saveUser(userInfo);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserByUsernameWhenFound() {
        String username = "admin";

        User user = new User();
        user.setUsername(username);
        when(userRepo.findByUsername(username)).thenReturn(user);

        UserInfo userInfo = null;
        try {
            userInfo = userService.getUser(username);
        } catch (UserNotFoundException e) {
            fail();
        }

        assertNotNull(userInfo);
        assertEquals(username, userInfo.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByUsernameWhenNotFound() throws UserNotFoundException {
        String username = "admin";
        userService.getUser(username);
    }

}
