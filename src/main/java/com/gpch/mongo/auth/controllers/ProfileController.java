package com.gpch.mongo.auth.controllers;


import com.gpch.mongo.security.TokenAuthentication;
import com.gpch.mongo.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author Jaidon Jaekel
 *
 * This class is the controller the profile page requests
 *
 */
@SuppressWarnings("unused")
@Controller
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param model Model
     * @param authentication Authentication
     * @return String
     *
     * Handles profile page requests and add the user's infomation to a profile model
     *
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    protected String profile(final Model model, final Authentication authentication) {

        // Since we've configured Spring Security to only allow authenticated requests to
        // reach this endpoint, and we control the Authentication implementation, we can safely cast.
        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        if (tokenAuthentication == null) {
            return "redirect:/login";
        }

        String profileJson = TokenUtils.claimsAsJson(tokenAuthentication.getClaims());

        model.addAttribute("profile", tokenAuthentication.getClaims());
        model.addAttribute("profileJson", profileJson);
        return "profile";
    }

}
