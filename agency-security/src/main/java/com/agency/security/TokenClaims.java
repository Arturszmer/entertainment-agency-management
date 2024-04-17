package com.agency.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenClaims {

    private Collection<GrantedAuthority> authorities;
    @JsonProperty(value = "sub")
    private String username;
    private String email;
    private String scope;
}
