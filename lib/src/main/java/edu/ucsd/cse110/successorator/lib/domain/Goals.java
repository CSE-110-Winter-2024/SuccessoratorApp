package edu.ucsd.cse110.successorator.lib.domain;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
public class Goals {
    @NonNull
    public static List<Goal> reorder(List<Goal> cards) {
        var newCards = new ArrayList<Goal>();
        for(int i = 0; i < cards.size(); i++){
            var thisGoal = cards.get(i);
            newCards.add(thisGoal.withSortOrder(i + 1));
        }

        return newCards;
    }

}
