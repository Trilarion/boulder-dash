package boulderdash.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Observer interface
 */
public interface Observer<E> {

    void update(@NotNull E notification);
}
