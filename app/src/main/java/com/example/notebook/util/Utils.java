package com.example.notebook.util;

import androidx.annotation.Nullable;

import java.util.Collection;

public abstract class Utils {

    public Utils() {
    }

    public static <T> T singleResult(@Nullable Collection<T> results) throws IncorrectResultSizeDataAccessException {
        if (results.isEmpty()) {
            return null;
        } else if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        } else {
            return results.iterator().next();
        }
    }
}
