package com.brampel.tdserver.model;

import org.springframework.security.core.userdetails.UserDetails;

public interface TDUserDetails extends UserDetails {
    long getID();
    String getIDasString();
}
