package com.example.cfs;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.security.UserGroupInformation;

import com.datastax.bdp.hadoop.cfs.CassandraFileSystem;

public class Writer {
	
	// Open ports

	public static void main(String[] args) throws Exception {

		FSDataOutputStream o = null;
		CassandraFileSystem cfs = null;
		String content = "some text content..";

		try {
			System.setProperty("cassandra.config", ClassLoader.getSystemResource("conf/cassandra.yaml").toString());
			System.setProperty("dse.config", ClassLoader.getSystemResource("conf/dse.yaml").toString());

			Configuration conf = new Configuration();
			conf.addResource(new Path("conf/core-site.xml"));

			UserGroupInformation.createUserForTesting("unixuserid", new String[] { "usergroupname" });
			UserGroupInformation.setConfiguration(conf);

			cfs = new CassandraFileSystem();
			cfs.initialize(URI.create("cfs://localhost:9160/"), conf);

			o = cfs.create(new Path("/test/testfile.txt"), true);
			o.write(content.getBytes());
			o.flush();

		} catch (Exception err) {
			System.out.println("Error: " + err.toString() +"\n");
			err.printStackTrace();
		} finally {
			if (o != null)
				o.close();
			if (cfs != null)
				cfs.close();
		}
	}
}
