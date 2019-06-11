import com.s3.SnekIO.restserver.User;
import com.s3.SnekIO.restserver.exception.ConflictException;
import com.s3.SnekIO.restserver.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserRepositoryMock implements UserRepository {

    private List<User> users;

    public UserRepositoryMock(List<User> users) {
        this.users = users;
    }

    public User register(User user) {
        // Check if email is already in use
        if (findUserByEmail(user.email.toLowerCase()) != null) {
            throw new ConflictException("This email address is already in use.");
        }

        // Hash the password using BCrypt
        //user.setPassword(BCrypt.hashpw(user.password, BCrypt.gensalt(10)));

        users.add(user);
        return user;
    }

    @Override
    public User findBy_id(ObjectId _id) {
        for (User user : users) {
            if (user._id == _id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        for (User user : users) {
            if (user.email.equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public <S extends User> S save(S s) {
        for (User user : users) {
            // Update existing user
            if (user._id == s._id) {
                user._id = s._id;
                user.userName = s.userName;
                user.email = s.email;
                user.password = s.password;
                return s;
            }
        }
        return s;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> S insert(S s) {
        return null;
    }

    @Override
    public <S extends User> List<S> insert(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }
}
