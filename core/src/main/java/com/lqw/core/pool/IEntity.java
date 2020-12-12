package com.lqw.core.pool;

import java.io.Serializable;

public interface IEntity<K extends Serializable> extends Serializable {
	K getId();

	String getFeature_code();
	
}