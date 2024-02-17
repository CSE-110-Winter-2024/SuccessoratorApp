package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
public class Goals {
    @NonNull
    public static List<Goal> reorder(List<Goal> cards) {
        //Build mapping from old to new sort orders using Math.floorMod to wrap around
        var newGoals = new ArrayList<Goal>();
        for (int i = 0; i < cards.size(); i++) {
            var thisCard = cards.get(i);
            var thatCard = cards.get(Math.floorMod(i, cards.size()));
            if(!thisCard.isComplete()) {
                newGoals.add(thisCard.withSortOrder(thatCard.getSortOrder()));
            }
        }
        return newGoals;
    }
}
