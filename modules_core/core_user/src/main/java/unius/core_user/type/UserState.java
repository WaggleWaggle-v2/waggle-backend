package unius.core_user.type;

public enum UserState {
    INCOMPLETE("INCOMPLETE"),
    VERIFIED("VERIFIED"),
    BLOCKED("BLOCKED"),
    WITHDRAW("WITHDRAW"),
    ADMIN("ADMIN");

    private final String description;

    UserState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
