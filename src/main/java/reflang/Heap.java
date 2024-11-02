package reflang;

import java.util.TreeSet;

/**
 * Representation of a heap, which maps references to values.
 *
 * @author hridesh
 */
public interface Heap {

    Value ref(Value value);

    Value deref(Value.RefVal loc);

    Value setref(Value.RefVal loc, Value value);

    Value free(Value.RefVal value);

    class Heap16Bit implements Heap {
        static final int HEAP_SIZE = 65_536;

        Value[] _rep = new Value[HEAP_SIZE];
        TreeSet<Integer> ts = new TreeSet();

        public Value ref(Value value) {
            if (ts.isEmpty()) return new Value.DynamicError("Out of memory error");
            Value.RefVal new_loc = new Value.RefVal(ts.first());
            _rep[ts.first()] = value;
            ts.removeFirst();
            return new_loc;
        }

        public Value deref(Value.RefVal loc) {
            try {
                if (_rep[loc.loc()] == null) return new Value.DynamicError("Null pointer at " + loc);
                return _rep[loc.loc()];
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }
        }

        public Value setref(Value.RefVal loc, Value value) {
            try {
                if (_rep[loc.loc()] == null) return new Value.DynamicError("Null pointer at " + loc);
                return _rep[loc.loc()] = value;
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }
        }

        public Value free(Value.RefVal loc) {
            try {
                ts.add(loc.loc());
                _rep[loc.loc()] = null;
                return loc;
            } catch (ArrayIndexOutOfBoundsException e) {
                return new Value.DynamicError("Segmentation fault at access " + loc);
            }
        }

        public Heap16Bit() {
            for(int i=0; i<HEAP_SIZE; i++) ts.add(i);
        }
    }

}
