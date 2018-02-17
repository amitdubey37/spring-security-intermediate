package com.springmvc.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * Created by amitdubey on 17/02/18.
 */

@Service
public class RunAsService {
    @Secured({"ROLE_RUN_AS_CUSTOM"})
    public String display() {
        return "Display";
    }
}
