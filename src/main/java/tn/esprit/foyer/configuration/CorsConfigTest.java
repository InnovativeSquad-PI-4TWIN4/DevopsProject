package tn.esprit.foyer.configuration;


import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    @Test
    void testCorsConfigurerBeanIsCreated() {
        CorsConfig config = new CorsConfig();
        WebMvcConfigurer webMvcConfigurer = config.corsConfigurer();
        assertNotNull(webMvcConfigurer, "Le bean WebMvcConfigurer ne doit pas être null");
    }
}
