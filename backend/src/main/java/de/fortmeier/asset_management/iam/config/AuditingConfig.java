package de.fortmeier.asset_management.iam.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configurable
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    AuditorAware<String> securityContextHolderAuditorAware() {
        return () -> Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }
}
