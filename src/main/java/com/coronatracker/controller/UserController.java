package com.coronatracker.controller;

import static com.coronatracker.util.Util.getUserId;

import com.coronatracker.dto.BulkContactDetailsDTO;
import com.coronatracker.dto.UserDetailsDTO;
import com.coronatracker.service.ContactDetailsService;
import com.coronatracker.service.UserService;
import com.google.common.collect.ImmutableMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sampath Katari on 26/03/20.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactDetailsService contactDetailsService;

    @GetMapping(value = "")
    public ResponseEntity getUserInfo(final HttpServletRequest request) {
        return ResponseEntity.ok(userService.getUser(getUserId(request)));
    }

    @PutMapping(value = "")
    public ResponseEntity updateInfo(final HttpServletRequest request, @RequestBody final UserDetailsDTO userDetailsDTO) {
        return ResponseEntity.ok(userService.updateUserInfo(getUserId(request), userDetailsDTO));
    }

    @PutMapping(value = "/markpositive")
    public ResponseEntity updateCovidStatusPositive(final HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateUserCovidStatusPositive(getUserId(request)));
    }

    @PostMapping(value = "/bulkContactDetails")
    public ResponseEntity bulkContactDetails(final HttpServletRequest request, @RequestBody final BulkContactDetailsDTO bulkContactDetailsDTO) {
        userService.bulkContactDetails(getUserId(request), bulkContactDetailsDTO);
        return ResponseEntity.ok(ImmutableMap.of());
    }

}