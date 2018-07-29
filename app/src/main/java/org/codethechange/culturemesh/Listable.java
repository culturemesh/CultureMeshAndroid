package org.codethechange.culturemesh;

/**
 * Interface for objects that need to be listed in the user interface.
 */
public interface Listable {

    int MAX_CHARS = 30;
    String ellipses = "...";

    /**
     * Get a label (maximum of {@link Listable#MAX_CHARS} characters long) to display as an
     * identifier for the object.
     * @return Displayable name for the object, which must be less than or equal to
     * {@link Listable#MAX_CHARS} characters long
     */
    String getListableName();
}
