package siea.dev.shearable.registry;

public enum DropState {
    DEFAULT,
    SHEARABLE,
    DISABLED;

    public DropState next() {
        return switch (this) {
            case DEFAULT -> SHEARABLE;
            case SHEARABLE -> DISABLED;
            case DISABLED -> DEFAULT;
        };
    }
}
