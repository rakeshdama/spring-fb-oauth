package com.security.springfboauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @Autowired
    OAuth2AuthorizedClientService auth2AuthorizedClientService;

    @RequestMapping("/oauth2LoginSuccess")
    public String getOauth2LoginInfo(Model model, @AuthenticationPrincipal OAuth2AuthenticationToken authenticationToken) {
        System.out.println(authenticationToken.getAuthorizedClientRegistrationId());
        System.out.println(authenticationToken.getName());

        OAuth2User user = authenticationToken.getPrincipal();
        System.out.println("User Id: " + user.getName());
        System.out.println("Email Id: " + user.getAttributes().get("email"));

        OAuth2AuthorizedClient client = auth2AuthorizedClientService
                .loadAuthorizedClient(authenticationToken.getAuthorizedClientRegistrationId(),
                        authenticationToken.getName()
                );
        System.out.println("Token Value" + client.getAccessToken().getTokenValue());

        model.addAttribute("name", user.getAttribute("name"));
        return "home";
    }

    @RequestMapping("/formLoginSuccess")
    public String getFormLoginInfo(Model model, @AuthenticationPrincipal Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "home";
    }
}
