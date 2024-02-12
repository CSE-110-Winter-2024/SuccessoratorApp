package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Goal class
 * title The title of the goal.
 * id The unique identifier for the goal.
 * done The completion status of the goal; true if the goal is completed, false otherwise.
 */
public class Goal implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String title;
    private final boolean isComplete;
    private final Integer sortOrder; // Changed from 'int' to 'Integer' to allow null values.

    public Goal(
            @NonNull String title,
            @Nullable Integer id,
            @NonNull String title,
            boolean isComplete,
            Integer sortOrder // Corrected parameter type and removed the semicolon.
    ) {
        this.id = id;
        this.title = title;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
    }

    public @Nullable Integer id() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isComplete == goal.isComplete &&
                Objects.equals(id, goal.id) &&
                Objects.equals(title, goal.title) &&
                Objects.equals(sortOrder, goal.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isComplete, sortOrder);
    }
}