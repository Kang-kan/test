package com.xxgame.logsrv.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可并发的hashset
 * @author gil
 *
 * @param <E>
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable {

	private static final long serialVersionUID = -8976579348913944488L;

	private final ConcurrentHashMap<E, Boolean> values = new ConcurrentHashMap<E, Boolean>();

	public ConcurrentHashSet(Collection<E> c) {
		addAll(c);
	}

	public ConcurrentHashSet() {
	}

	@Override
	public boolean add(E o) {
		return values.putIfAbsent(o, Boolean.TRUE) == null;
	}

	@Override
	public int size() {
		return values.size();
	}

	@Override
	public boolean contains(Object o) {
		return values.containsKey(o);
	}

	@Override
	public Iterator<E> iterator() {
		return values.keySet().iterator();
	}

	@Override
	public boolean remove(Object o) {
		return values.remove(o) != null;
	}

	@Override
	public void clear() {
		values.clear();
	}

}
