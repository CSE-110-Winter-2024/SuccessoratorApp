package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Goals {
    //todo shift the ordering of completed and uncompleted goals
    @NonNull
    public static List<Goal> shiftOrder(List<Goal> goals) {
        // Get the current sort order of each card.
        var sortOrders = goals.stream()
                .map(Goal::getSortOrder)
                .collect(Collectors.toList());

        // Shuffle the sort orders.
        Collections.shuffle(sortOrders);

        // Map each card to a new card with the shuffled sort order.
        var newCards = goals.stream()
                .map(card -> card.withSortOrder(sortOrders.remove(0)))
                .collect(Collectors.toList());

        return newCards;
    }
}
