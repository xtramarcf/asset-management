package de.fortmeier.asset_management.iam.requests;

public record UserProjection(
        String firstName,
        String lastName,
        String userName,
        boolean enabled
) {
}
