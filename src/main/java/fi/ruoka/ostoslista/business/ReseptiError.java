package fi.ruoka.ostoslista.business;

public class ReseptiError extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String message;

    public ReseptiError(String message) {
        super("Resepti update failed: " + message.toString());
        this.message = message;
    }

    public String getMessages() {
        return message;
    }

}
