package pl.sda.mysampleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.mysampleblog.model.Role;
import pl.sda.mysampleblog.model.User;
import pl.sda.mysampleblog.repository.RoleRepository;
import pl.sda.mysampleblog.repository.UserRepository;

import java.util.List;


@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean passwordCheck(String password, String password_confirm) {
        if (password.equals(password_confirm)) {
            return true;
        }
        return false;
    }

    public void addNewUser(User user) {
        user.addRole(roleRepository.getOne(1L));
        user.setActivityFlag(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Role getAdminRole(){
        return roleRepository.getOne(2L);
    }

    public User getUserById(Long Id){
        return userRepository.getOne(Id);
    }

    public void addAdminRole(Long Id) {
        User user = getUserById(Id);
        user.addRole(getAdminRole());
        userRepository.save(user);
    }

    public void subAdminRole(Long Id) {
        User user = getUserById(Id);
        user.subRole(getAdminRole());
        userRepository.save(user);
    }
}
