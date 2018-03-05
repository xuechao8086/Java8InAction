package lambdasinaction.xuechao8086.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author gumi
 * @since 2018/03/05 14:00
 */
public class RxJavaDemo {
    private static class LocalObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {
            System.out.println("subscribe");
        }

        @Override
        public void onNext(T element) {
            System.out.println("onNext: " + element.toString());
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("error, msg: " + e.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("complete");
        }
    }

    @SafeVarargs
    private final <T> ObservableOnSubscribe<T> buildObservableOnSubscribe(T... elements) {
        return emitter -> {
            for(T element : elements) {
                emitter.onNext(element);
            }
            emitter.onComplete();
        };
    }

    public static void main(String[] args) {
        Observer<Integer> observer = new LocalObserver<>();

        RxJavaDemo rxJavaDemo = new RxJavaDemo();
        Observable<Integer> observable = Observable.create(rxJavaDemo.buildObservableOnSubscribe(1, 2, 3));
        observable.subscribe(observer);
        observable.subscribe(t -> System.out.println("consume element: " + t));
    }
}


