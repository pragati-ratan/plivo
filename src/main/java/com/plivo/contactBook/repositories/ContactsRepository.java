package com.plivo.contactBook.repositories;

import com.plivo.contactBook.models.Contacts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends PagingAndSortingRepository<Contacts, Integer> {
    List<Contacts> getByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Contacts> getByEmailIdContainingIgnoreCase(String name, Pageable pageable);
    long countByNameContainingIgnoreCase(String name);
    long countByEmailIdContainingIgnoreCase(String name);
}
