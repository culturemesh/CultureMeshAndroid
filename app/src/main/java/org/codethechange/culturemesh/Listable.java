package org.codethechange.culturemesh;

/**
 * Interface for objects that need to be listed in the user interface.
 */
public interface Listable {

    /**
     * Get a label to display as an identifier for the object
     * @return Displayable name for the object
     */
    String getListableName();

    /**
     * Get the number of users, which will be displayed under the person icon in lists
     * @return Number of users associated with the object
     */
    long getNumUsers();
}
