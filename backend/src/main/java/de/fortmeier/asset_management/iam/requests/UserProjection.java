package de.fortmeier.asset_management.iam.requests;

public interface UserProjection {
    String getFirstName();
    String getLastName();
    String getUserName();
    boolean getEnabled();
}
