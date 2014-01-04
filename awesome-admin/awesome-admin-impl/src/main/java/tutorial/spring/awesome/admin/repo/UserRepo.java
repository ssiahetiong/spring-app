package tutorial.spring.awesome.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import tutorial.spring.awesome.admin.entity.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String>,
        PagingAndSortingRepository<User, String> {

    User findByUsername(String username);

    List<User> findByName_FirstNameAndName_LastName(String firstName, String lastName);

}
