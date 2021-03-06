package tutorial.spring.awesome.admin.repo;

import com.google.common.collect.Lists;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import tutorial.spring.awesome.admin.config.AdminConfig;
import tutorial.spring.awesome.admin.config.TestResourceConfig;
import tutorial.spring.awesome.admin.entity.Contact;
import tutorial.spring.awesome.admin.entity.ContactType;
import tutorial.spring.awesome.admin.entity.Name;
import tutorial.spring.awesome.admin.entity.User;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AdminConfig.class, TestResourceConfig.class}, loader = AnnotationConfigContextLoader.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepoTest {

    private static final String ADMIN = "admin";
    private static final String USER = "user";

    @Autowired
    private UserRepo userRepo;

    @Test
    public void init() {

        User user1 = new User();
        user1.setId(1l);
        user1.setUsername(ADMIN);
        user1.setPassword("");
        user1.setName(new Name("test1", "test1", "test1"));
        userRepo.save(user1);

        User user2 = new User();
        user2.setId(2l);
        user2.setUsername(USER);
        user2.setPassword("");
        user2.setName(new Name("test2", "test2", "test2"));
        List<Contact> contacts = Lists.newArrayList();
        contacts.add(new Contact(ContactType.HOME, "11111"));
        contacts.add(new Contact(ContactType.MOBILE, "11111"));
        user2.setContacts(contacts);
        userRepo.save(user2);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userRepo.findAll();
        assertEquals(2, users.size());
    }

    @Test
    @Transactional(readOnly = true)
    public void testFindByUserName() {
        User user1 = userRepo.findByUsername(ADMIN);
        assertNotNull(user1);

        User user2 = userRepo.findByUsername(USER);
        assertNotNull(user2);
        assertEquals(2, user2.getContacts().size());
    }

    @Test
    public void testFindByFirstAndLastName() {
        List<User> users1 = userRepo.findAll();
        List<User> users = userRepo.findByName_FirstNameAndName_LastName("test2", "test2");
        assertFalse(users.isEmpty());
    }

}
