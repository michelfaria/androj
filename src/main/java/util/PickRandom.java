package util;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class PickRandom {
	public static <T> T in(List<T> l, Random r) {
		Objects.requireNonNull(l);
		Objects.requireNonNull(r);
		return l.get(r.nextInt(l.size()));
	}
}
