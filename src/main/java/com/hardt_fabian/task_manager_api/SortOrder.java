package com.hardt_fabian.task_manager_api;

/**
 * Enumeration über die unterstützen Sortierreihenfolgen.
 *
 * @author Fabian Hardt
 */
public enum SortOrder {
    /**
     * Aufsteigende Sortierreihenfolge
     */
    ASC,
    /**
     * Absteigende Sortierreihenfolge
     */
    DESC;

    /**
     * Gibt die korrespondierenden Sortierreihenfolge zum übergebenen Namen zurück. Groß- und
     * Kleinschreibung wird hierbei ignoriert.
     *
     * @param name String representation der gesuchten Sortierreihenfolge
     * @return die korrespondierenden Sortierreihenfolge zum übergebenen Namen
     */
    public static SortOrder fromString(String name) {
        try {
            return SortOrder.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ungültige Sortierreihenfolge: " + name);
        }
    }
}
