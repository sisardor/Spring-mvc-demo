package com.blackiceinc;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.PooledDataSource;

public class SomeClass {
	private String classs;

	public SomeClass() {
		super();
	}
	
	public void getConn(String dd) {
		PooledDataSource pdsX = C3P0Registry.pooledDataSourceByName(dd);
		System.out.println("SomeClass pds " + pdsX.getDataSourceName());
	}
}
