package application.pet.delivery.services;

import application.pet.delivery.entities.User;
import application.pet.delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void save(User user){
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

    public void deleteAll(){
        repository.deleteAll();
    }

}
