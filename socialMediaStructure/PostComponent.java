package socialMediaStructure;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Base component for the composite pattern used by posts and albums.
 *
 * <p>PostComponent represents either a leaf (simple Post) or a composite (Album)
 * and provides default implementations for composite operations that throw
 * UnsupportedOperationException. Concrete subclasses override methods as needed.
 *
 * Key fields:
 * - id: unique identifier for the component
 * - ownerId: id of the account that owns this component
 * - createdAt: timestamp when the component was created
 */
public abstract class PostComponent {
    protected String id;
    protected String ownerId;
    protected LocalDateTime createdAt;

    /**
     * Construct a PostComponent with an id and owner id. Sets createdAt to now.
     */
    public PostComponent(String id, String ownerId) {
        this.id = id;
        this.ownerId = ownerId;
        this.createdAt = LocalDateTime.now();
    }

    /** Returns the unique id for this component. */
    public String getId() {
        return id;
    }

    /** Returns the owner account id for this component. */
    public String getOwnerId() {
        return ownerId;
    }

    /** Returns the creation timestamp. */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Display the component to stdout. Implementations should honor the indent
     * to present nested structure (albums containing posts).
     *
     * @param indent indentation string for nested display
     */
    public abstract void display(String indent);

    /** Returns a short textual content summary for this component. */
    public abstract String getContent();

    // Composite convenience methods - leaves keep the defaults which throw.
    // Albums override these to manage children.
    public void add(PostComponent component) {
        throw new UnsupportedOperationException("Cannot add to this component");
    }

    public void remove(PostComponent component) {
        throw new UnsupportedOperationException("Cannot remove from this component");
    }

    public List<PostComponent> getChildren() {
        throw new UnsupportedOperationException("This component has no children");
    }
}
