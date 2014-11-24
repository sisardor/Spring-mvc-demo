package com.blackiceinc;

import java.beans.PropertyVetoException;
import java.util.Set;

import org.apache.cassandra.thrift.cassandraConstants;
import org.neo4j.cypher.internal.compiler.v2_0.untilMatched;

import snaq.db.ConnectionPoolManager;
import snaq.db.DBPoolDataSource;

import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

public class App {

	public static void main(String[] args) {
		DBPoolDataSource ds = new DBPoolDataSource();
		ds.setName("pool-ds");
		ds.setDescription("Pooling DataSource");
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.1.101:3306/ReplicantDB");
		ds.setUser("Deckard");
		ds.setPassword("TyrellCorp1982");
		ds.setMinPool(5);
		ds.setMaxPool(10);
		ds.setMaxSize(30);
		ds.setIdleTimeout(3600); // Specified in seconds.
		ds.setValidationQuery("SELECT COUNT(*) FROM Replicants");

		System.out.println(ds.getDescription());

		ConnectionPoolManager cpm = null;
		PooledDataSource pds = C3P0Registry.pooledDataSourceByName("sar");
		System.out.println("pds " + pds);
		if (pds == null) {
			ComboPooledDataSource cpds = new ComboPooledDataSource("sar");
			cpds.setJdbcUrl("jdbc:sqlserver://localhost;databaseName=" + "sar");
			cpds.setUser("username");
			cpds.setPassword("password");
			cpds.setInitialPoolSize(16);
			cpds.setMaxConnectionAge(10000);
			try {
				cpds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("cpds " + cpds.getDescription());
		}
		
		PooledDataSource pdsX = C3P0Registry.pooledDataSourceByName("jasur");
		if (pdsX == null) {
			ComboPooledDataSource cpds = new ComboPooledDataSource("jasur");
			cpds.setJdbcUrl("jdbc:sqlserver://localhost;databaseName=" + "jasur");
			cpds.setUser("username");
			cpds.setPassword("password");
			cpds.setInitialPoolSize(16);
			cpds.setMaxConnectionAge(10000);
			try {
				cpds.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("cpds " + cpds.getDataSourceName());
			//cpds.close();
		} else
			System.out.println("pds " + pdsX.getDataSourceName());
		
		SomeClass sd = new SomeClass();
		sd.getConn("sar");
		sd.getConn("jasur");
		Set set = C3P0Registry.getPooledDataSources();
	    int sz = set.size();
	    if ( sz == 1 ) // yay, just one DataSource
	      System.out.println(set.iterator().next());
	    else 
	      throw new RuntimeException("No unique c3p0 DataSource, found:" + sz);
	}

}
