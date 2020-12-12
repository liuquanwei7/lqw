package com.lqw.core.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;
import com.lqw.core.exception.LogicException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liuquanwei
 * @date 2020/12/12
 */
public abstract class Checker {
	public static boolean isBlank(CharSequence cs) {
		return StringUtils.isBlank(cs);
	}

	public static void isBlank(CharSequence cs, String message) {
		if (isBlank(cs)) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static boolean notBlank(CharSequence cs) {
		return StringUtils.isNotBlank(cs);
	}

	public static void notBank(CharSequence cs, String message) {
		if (notBlank(cs)) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static boolean isNull(Object obj) {
		return (obj == null);
	}

	public static void isNull(Object obj, String message) {
		if (obj == null) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static <T> void isNull(T obj, Consumer<? super T> consumer) {
		if (obj == null) {
			consumer.accept(obj);
		}
	}

	public static boolean notNull(Object obj) {
		return (obj != null);
	}

	public static void notNull(Object obj, String message) {
		if (obj != null) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static <T> void notNull(T obj, Consumer<? super T> consumer) {
		if (obj != null) {
			consumer.accept(obj);
		}
	}

	public static <T> boolean isEmpty(Collection<T> coll) {
		if (coll == null) {
			return true;
		}
		return coll.isEmpty();
	}

	public static <T> void isEmpty(Collection<T> coll, String message) {
		if (coll == null) {
			throw LogicException.valueOfUnknow(message);
		}
		if (coll.isEmpty()) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static <T> boolean notEmpty(Collection<T> coll) {
		if (coll == null) {
			return false;
		}
		return !coll.isEmpty();
	}

	public static <T> void notEmpty(Collection<T> coll, String message) {
		if (coll == null) {
			return;
		}
		if (coll.isEmpty()) {
			return;
		}
		throw LogicException.valueOfUnknow(message);
	}

	public static <T> void notEmpty(Collection<T> coll, Consumer<? super Collection<T>> consumer) {
		if (coll == null) {
			return;
		}
		if (coll.isEmpty()) {
			return;
		}
		consumer.accept(coll);
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		if (map == null) {
			return true;
		}
		return map.isEmpty();
	}

	public static <K, V> void isEmpty(Map<K, V> map, String message) {
		if (map == null) {
			throw LogicException.valueOfUnknow(message);
		}
		if (map.isEmpty()) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static <K, V> boolean notEmpty(Map<K, V> map) {
		if (map == null) {
			return false;
		}
		return !map.isEmpty();
	}

	public static <K, V> void notEmpty(Map<K, V> map, String message) {
		if (map == null) {
			return;
		}
		if (map.isEmpty()) {
			return;
		}
		throw LogicException.valueOfUnknow(message);
	}

	public static boolean isEq(Number a, Number b) {
		if (!a.getClass().equals(b.getClass())) {
			throw LogicException.valueOfUnknow("");
		} return a.equals(b);
	}

	public static void isEq(Number a, Number b, String message) {
		if (!a.getClass().equals(b.getClass())) {
			throw LogicException.valueOfUnknow("");
		} if (a.equals(b)) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static boolean isNe(Number a, Number b) {
		if (!a.getClass().equals(b.getClass())) {
			throw LogicException.valueOfUnknow("");
		} return !a.equals(b);
	}

	public static void isNe(Number a, Number b, String message) {
		if (!a.getClass().equals(b.getClass())) {
			throw LogicException.valueOfUnknow("");
		} if (!a.equals(b)) {
			throw LogicException.valueOfUnknow(message);
		}
	}

	public static boolean equals(CharSequence cs1, CharSequence cs2) {
		return StringUtils.equals(cs1, cs2);
	}

	public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
		return StringUtils.equalsIgnoreCase(str1, str2);
	}

	public static <T> boolean isUnique(Collection<T> list) {
		return list.stream().allMatch(new HashSet()::add);
	}

	public static <T> boolean notUnique(Collection<T> list) {
		return !isUnique(list);
	}

	public static <T> void notUnique(Collection<T> list, String message) {
		if (notUnique(list)) {
			throw LogicException.valueOfUnknow(message);
		}
	}
}
