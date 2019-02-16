package com.plivo.contactBook.controllers;

import com.plivo.contactBook.dto.*;
import com.plivo.contactBook.models.Contacts;
import com.plivo.contactBook.security.services.UserAuthenticationService;
import com.plivo.contactBook.services.ContactsService;
import com.plivo.contactBook.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContactsController {
    public final ContactsService contactsService;
    public final UserAuthenticationService userAuthenticationService;

    @GetMapping(value = "/contacts", produces = "application/json")
    public ResponseDTO getAdvertiser(HttpServletResponse response,
                                     @RequestParam final Optional<Enums.ContactSearchEnum> searchBy,
                                     @RequestParam final Optional<String> searchValue,
                                     @RequestParam final Optional<String> sortKey,
                                     @RequestParam final Optional<String> sortOrder,
                                     @RequestParam final Optional<Integer> limit,
                                     @RequestParam final Optional<Integer> page){
        FindAllResponseDTO<Contacts> findAllResponseDTO = contactsService.getContacts(searchBy, searchValue, sortKey, sortOrder, limit, page);
        ResponseDTO responseDTO = new ResponseDTO(findAllResponseDTO);
        response.setStatus(Constants.StatusCodes.SUCCESS);
        return responseDTO;
    }

    @PostMapping(path = "/contacts", consumes = "application/json", produces = "application/json")
    public ResponseDTO addContact(@NotNull @Valid @RequestBody AddContactDTO addContactDTO,
                        HttpServletResponse response) {
        ResponseDTO responseDTO = new ResponseDTO();
        contactsService.addContact(addContactDTO);
        response.setStatus(Constants.StatusCodes.CREATED);
        responseDTO.setStatus(Constants.StatusCodes.CREATED);
        responseDTO.setMessage("Contact added successfully");
        return responseDTO;
    }

    @PutMapping(path = "/contacts/{id}", consumes = "application/json", produces = "application/json")
    public ResponseDTO updateContact(@NotNull @Valid @RequestBody UpdateContactDTO updateContactDTO, @PathVariable("id") Integer id,
                           HttpServletResponse response) {
        ResponseDTO responseDTO = new ResponseDTO();
        contactsService.updateContact(id, updateContactDTO);
        response.setStatus(Constants.StatusCodes.SUCCESS);
        responseDTO.setStatus(Constants.StatusCodes.SUCCESS);
        responseDTO.setMessage("Contact updates successfully");
        return responseDTO;
    }
}
