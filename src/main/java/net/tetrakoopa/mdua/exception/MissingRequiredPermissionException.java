package net.tetrakoopa.mdua.exception;

public class MissingRequiredPermissionException extends RuntimeException {

    private final String [] permissionNames;
    private final static String NOTHING[] = new String[0];

    public MissingRequiredPermissionException() {
        this(NOTHING);
    }
    public MissingRequiredPermissionException(String ...permissionNames) {
        this.permissionNames = permissionNames == null ? NOTHING : permissionNames;
    }
}
