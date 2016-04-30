package net.tetrakoopa.mdua.exception;

public class MissingNeededException extends RuntimeException {

    private final String [] permissionNames;
    private final static String NOTHING[] = new String[0];

    public MissingNeededException() {
        this(NOTHING);
    }
    public MissingNeededException(String ...permissionNames) {
        this.permissionNames = permissionNames == null ? NOTHING : permissionNames;
    }
}
