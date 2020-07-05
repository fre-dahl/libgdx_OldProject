package graphics.drwdat.abstr;

import com.badlogic.gdx.utils.Array;

public abstract class DrwPool<T> {

    /** Modified version of the gdx.utils.Pool class for DrwDat spesific use.
     * see. gdx.utils.Pool
     */

    public final int max;
    public int peak;

    private final Array<T> freeObjects;

    public DrwPool() {
        this(16, Integer.MAX_VALUE, false);
    }

    public DrwPool (int initialCapacity) {
        this(initialCapacity, Integer.MAX_VALUE, false);
    }

    public DrwPool (int initialCapacity, int max) {
        this(initialCapacity, max, false);
    }

    public DrwPool (int initialCapacity, int max, boolean preFill) {
        if (initialCapacity > max && preFill)
            throw new IllegalArgumentException("max must be larger than initialCapacity if preFill is set to true.");
        freeObjects = new Array(false, initialCapacity);
        this.max = max;
        if (preFill) {
            for (int i = 0; i < initialCapacity; i++)
                freeObjects.add(newObject());
            peak = freeObjects.size;
        }
    }

    abstract protected T newObject ();

    // This is changed for DrwDat
    public T obtain () {
        if (freeObjects.size == 0) return newObject();
        T object = freeObjects.pop();
        if (object instanceof DrwPool.DrwPoolable)
            ((DrwPoolable)object).onSpawm();
        return object;
    }

    public void free (T object) {
        if (object == null) throw new IllegalArgumentException("object cannot be null.");
        if (freeObjects.size < max) {
            freeObjects.add(object);
            peak = Math.max(peak, freeObjects.size);
        }
        reset(object);
    }

    public void fill (int size) {
        for (int i = 0; i < size; i++)
            if (freeObjects.size < max) freeObjects.add(newObject());
        peak = Math.max(peak, freeObjects.size);
    }

    protected void reset (T object) {
        if (object instanceof DrwPool.DrwPoolable) ((DrwPoolable)object).reset();
    }

    public void freeAll (Array<T> objects) {
        if (objects == null) throw new IllegalArgumentException("objects cannot be null.");
        Array<T> freeObjects = this.freeObjects;
        int max = this.max;
        for (int i = 0; i < objects.size; i++) {
            T object = objects.get(i);
            if (object == null) continue;
            if (freeObjects.size < max) freeObjects.add(object);
            reset(object);
        }
        peak = Math.max(peak, freeObjects.size);
    }

    // This is changed for DrwDat
    public void clear () {
        for (T object : freeObjects) {
            if (object instanceof DrwPool.DrwPoolable) ((DrwPoolable)object).onClear();
        }
        freeObjects.clear();
    }

    public int getFree () {
        return freeObjects.size;
    }

    // This is changed for DrwDat
    static public interface DrwPoolable {
        public void reset (); // call drwDat.dispose here
        public void onClear(); // call drwDat.dispose here
        public void onSpawm(); // call drwDat.add here
    }
}
