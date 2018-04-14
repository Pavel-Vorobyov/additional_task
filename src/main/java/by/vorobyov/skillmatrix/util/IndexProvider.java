package by.vorobyov.skillmatrix.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class IndexProvider {

    private AtomicInteger index;

    public IndexProvider() {
        index = new AtomicInteger(0);
    }

    public int getLastIndex(){
        return index.incrementAndGet();
    }
}
