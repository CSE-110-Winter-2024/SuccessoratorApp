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

    private final String state;//State state;

    private final Integer recurringId;
    private final Integer contextId;

    public Goal(
            @NonNull String title,
            @Nullable Integer id,
            boolean isComplete,
            Integer sortOrder, // Corrected parameter type and removed the semicolon.
            String state, //State state,
            Integer recurringId,
            Integer contextId
    ) {
        this.id = id;
        this.title = title;
        this.isComplete = isComplete;
        this.sortOrder = sortOrder;
        this.state = state;
        this.recurringId = recurringId;
        this.contextId = contextId;
    }

    public @Nullable Integer getId() {
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

    public String getState(){return state;}

    public Integer getRecurringId(){return recurringId;}

    public Integer getContextId(){return contextId;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goal goal = (Goal) o;
        return isComplete == goal.isComplete &&
                Objects.equals(id, goal.id) &&
                Objects.equals(title, goal.title) &&
                Objects.equals(sortOrder, goal.sortOrder) &&
                Objects.equals(state, goal.state) &&
                Objects.equals(recurringId, goal.recurringId) &&
                Objects.equals(contextId, goal.contextId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title,
                isComplete, sortOrder,
                state, recurringId, contextId);
    }

    public Goal withId(int id) {
        return new Goal(this.title, id,
                this.isComplete, this.sortOrder,
                this.state, this.recurringId,
                this.contextId);
    }

    public Goal withSortOrder(int sortOrder) {
        return new Goal(this.title, this.id,
                this.isComplete, sortOrder,
                this.state, this.recurringId,
                this.contextId);
    }

    public Goal withComplete(boolean isComplete){
        return new Goal(this.title, this.id,
                isComplete, this.sortOrder,
                this.state, this.recurringId,
                this.contextId);
    }

    public Goal withState(String state){
        return new Goal(this.title, this.id,
                this.isComplete, this.sortOrder,
                state, this.recurringId,
                this.contextId);
    }

    public Goal withRecurringId(Integer recurringId){
        return new Goal(this.title, this.id,
                this.isComplete, this.sortOrder,
                this.state, recurringId,
                this.contextId);
    }

    public Goal withContextId(Integer contextId){
        return new Goal(this.title, this.id,
                this.isComplete, this.sortOrder,
                this.state, this.recurringId,
                contextId);
    }

}