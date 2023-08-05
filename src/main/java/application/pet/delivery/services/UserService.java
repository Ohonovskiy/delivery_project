package application.pet.delivery.services;

import application.pet.delivery.entities.User;
import application.pet.delivery.enums.Role;
import application.pet.delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    public void remove(User user){
        repository.delete(user);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public User getById(Long id){
        return repository.getReferenceById(id);
    }
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAll(){
        List<User> users = repository.findAll();
        Collections.sort(users);
        return users;
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
