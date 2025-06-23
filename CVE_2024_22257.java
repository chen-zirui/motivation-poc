package edu.vision.se;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.security.vote.AccessDecisionVoter;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.providers.anonymous.AnonymousAuthenticationToken;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.vote.AuthenticatedVoter;


public class CVE_2024_22257 {

    @Test
    public void testAnonymousWorks() {
        AuthenticatedVoter voter = new AuthenticatedVoter();
        ConfigAttributeDefinition def = new ConfigAttributeDefinition(AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY);
        validateReturnValue(voter.vote(null, null, def));
    }

    public void validateReturnValue(int result) {
        assertThat(result).isEqualTo(AccessDecisionVoter.ACCESS_DENIED);
    }

    public Authentication createAnonymous() {
        GrantedAuthority[] authorities = new GrantedAuthority[] {new GrantedAuthorityImpl("ignored")};
        return new AnonymousAuthenticationToken("ignored", "ignored", authorities);
    }
}