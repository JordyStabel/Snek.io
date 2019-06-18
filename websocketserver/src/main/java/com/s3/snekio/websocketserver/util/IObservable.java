package com.s3.snekio.websocketserver.util;

public interface IObservable {
    void subscribe(IObserver observer);
    void unsubscribe(IObserver observer);
    void notifyAllObservers();
}
