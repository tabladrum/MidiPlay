package com.topo.miditest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NoteCombination {
    public static Set<String[]> permute(String[] notes)
    {
        // Use sets to eliminate semantic duplicates (aab is still aab even if you switch the two 'a's)
        // Switch to HashSet for better performance
//        List<String[]> set = new LinkedList<>();

        Set<String[]> set = new HashSet<>();
        // Termination condition: only 1 permutation for a string of length 1
        if (notes.length == 1)
        {
            set.add(notes);
        }
        else
        {
            // Give each character a chance to be the first in the permuted string
            for (int i=0; i<notes.length; i++)
            {
                // Remove the character at index i from the string
                List<String> pre = new LinkedList<String>();
                List<String> post = new LinkedList<>();
                for (int j = 0; j < i; j++) {
                    pre.add(notes[j]);
                }
                for (int j = i+1; j < notes.length; j++){
                    post.add(notes[j]);
                }
                List<String> newList = new LinkedList<>(pre);
                newList.addAll(post);
                String[] remaining = newList.toArray(new String[newList.size()]);

                // Recurse to find all the permutations of the remaining chars
                for (String[] permutation : permute(remaining))
                {
                    // Concatenate the first character with the permutations of the remaining chars
                    String[] toAdd = new String[1];
                    toAdd[0] = notes[i];

                    set.add(addArray(toAdd, permutation));
                }
            }
        }
        return set;
    }

    private static String[] addArray (String[] arr1, String[] arr2){
        Arrays.asList(arr1);
        List<String> newList = new LinkedList<>(Arrays.asList(arr1));
        newList.addAll(Arrays.asList(arr2));
        return (String[]) newList.toArray(new String[newList.size()]);
    }
}