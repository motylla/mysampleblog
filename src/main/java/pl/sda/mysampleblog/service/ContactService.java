package pl.sda.mysampleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.mysampleblog.model.Contact;
import pl.sda.mysampleblog.repository.ContactRepository;
import pl.sda.mysampleblog.repository.RoleRepository;
import pl.sda.mysampleblog.repository.UserRepository;

import java.util.List;

@Service
public class ContactService {
    UserRepository userRepository;
    ContactRepository contactRepository;
    RoleRepository roleRepository;

    @Autowired
    public ContactService(UserRepository userRepository, ContactRepository contactRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.roleRepository = roleRepository;
    }

    public void addContact(Contact contact) {
        contactRepository.save(contact);

    }

    public List<Contact> showContacts() {
        return contactRepository.findAll();
    }

    public void changeStatus(Long contact_id){
        Contact contact = contactRepository.getOne(contact_id);
        contact.setStatus(!contact.isStatus());
        contactRepository.save(contact);
    }

    public List<Contact> searchContacts(String pattern) {
        pattern = "%" + pattern + "%";
        return contactRepository.findAllByEmailLikeOrMessageLikeOrPhoneNumberLikeOrNameLike(pattern, pattern, pattern, pattern);

    }
}
