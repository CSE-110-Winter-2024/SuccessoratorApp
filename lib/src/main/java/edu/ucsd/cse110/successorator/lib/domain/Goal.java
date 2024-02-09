package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Goal class
 * title The title of the goal.
 * id The unique identifier for the goal.
 * done The completion status of the goal; true if the goal is completed, false otherwise.
 */
public class Goal {
    private final @Nullable String title;
    private final @Nullable Integer id;
    private final boolean done;

    public Goal(
            @Nullable String title,
            @Nullable Integer id,
            boolean done
    ) {
        this.id = id;
        this.title = title;
        this.done = done;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Goal goal = (Goal) o;
        return done == goal.done &&
                Objects.equals(id, goal.id) &&
                Objects.equals(title, goal.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, done);
    }
}
