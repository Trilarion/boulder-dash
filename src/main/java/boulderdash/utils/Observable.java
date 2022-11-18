package boulderdash.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Type-safe, thread safe Observable for the Observer interface. Uses CopyOnWriteArrayList for thread safety.
 */
public class Observable<E> {
    private final Collection<Observer<E>> observers = new CopyOnWriteArrayList<>();

    public void addObserver(@NotNull Observer<E> observer) {
        observers.add(observer);
    }

    public void removeObserver(@NotNull Observer<E> observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(@NotNull E notification) {
        for (Observer<E> observer : observers) {
            observer.update(notification);
        }
    }
}
