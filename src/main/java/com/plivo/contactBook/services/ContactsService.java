package com.plivo.contactBook.services;

import com.plivo.contactBook.dto.AddContactDTO;
import com.plivo.contactBook.dto.Enums;
import com.plivo.contactBook.dto.FindAllResponseDTO;
import com.plivo.contactBook.dto.UpdateContactDTO;
import com.plivo.contactBook.models.Contacts;
import com.plivo.contactBook.repositories.ContactsRepository;
import com.plivo.contactBook.utils.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactsService {
    public final ContactsRepository contactsRepository;

    public FindAllResponseDTO<Contacts> getContacts(Optional<Enums.ContactSearchEnum> searchBy,
                                                    Optional<String> searchValue,
                                                    Optional<String> sortKey,
                                                    Optional<String> sortOrder,
                                                    Optional<Integer> limit,
                                                    Optional<Integer> page) {
        FindAllResponseDTO<Contacts> findAllResponseDTO = new FindAllResponseDTO<>();
        if (searchBy.isPresent() && searchValue.isPresent() && searchValue.get() != null && !searchValue.get().equalsIgnoreCase("null") && !searchValue.get().isEmpty()) {
            switch (searchBy.get()) {
                case name: {
                    findAllResponseDTO.setData(contactsRepository.getByNameContainingIgnoreCase(searchValue.get(), Pagination.getPageable(limit, page, sortKey, sortOrder)));
                    findAllResponseDTO.setCount(contactsRepository.countByNameContainingIgnoreCase(searchValue.get()));
                }
                break;
                case emailId: {
                    findAllResponseDTO.setData(contactsRepository.getByEmailIdContainingIgnoreCase(searchValue.get(), Pagination.getPageable(limit, page, sortKey, sortOrder)));
                    findAllResponseDTO.setCount(contactsRepository.countByEmailIdContainingIgnoreCase(searchValue.get()));
                }
                break;
                default: {
                    findAllResponseDTO.setData(contactsRepository.findAll(Pagination.getPageable(limit, page, sortKey, sortOrder)).getContent());
                    findAllResponseDTO.setCount(contactsRepository.count());
                }
            }
        } else {
            findAllResponseDTO.setData(contactsRepository.findAll(Pagination.getPageable(limit, page, sortKey, sortOrder)).getContent());
            findAllResponseDTO.setCount(contactsRepository.count());
        }
        return findAllResponseDTO;
    }

    public void addContact(AddContactDTO addContactDTO) {
        Contacts contacts = new Contacts();
        contacts.setEmailId(addContactDTO.getEmailId());
        contacts.setName(addContactDTO.getName());
        contacts.setAddress(addContactDTO.getAddress());
        contacts.setContactNumber(addContactDTO.getContactNumber());
        contacts.setTag(addContactDTO.getTag());
        contactsRepository.save(contacts);
    }

    public void updateContact(Integer id, UpdateContactDTO updateContactDTO) {
        Optional<Contacts> contacts = contactsRepository.findById(id);
        if (contacts.isPresent()) {
            if (updateContactDTO.getEmailId() != null) {
                contacts.get().setEmailId(updateContactDTO.getEmailId());
            }
            if (updateContactDTO.getName() != null) {
                contacts.get().setName(updateContactDTO.getName());
            }
            if (updateContactDTO.getContactNumber() != null) {
                contacts.get().setContactNumber(updateContactDTO.getContactNumber());
            }
            if (updateContactDTO.getAddress() != null) {
                contacts.get().setAddress(updateContactDTO.getAddress());
            }
            if (updateContactDTO.getTag() != null) {
                contacts.get().setTag(updateContactDTO.getTag());
            }
            contactsRepository.save(contacts.get());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contact not found");
        }

    }
}
