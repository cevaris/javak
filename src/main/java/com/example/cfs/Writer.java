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
		FileSystem cfs = null;
		String content = "some text content..";

		try {
			System.setProperty("cassandra.config", ClassLoader.getSystemResource("conf/cassandra.yaml").toString());
			System.setProperty("dse.config", ClassLoader.getSystemResource("conf/dse.yaml").toString());

			Configuration conf = new Configuration();
			conf.addResource(new Path("/usr/local/dse/resources/hadoop/conf/core-site.xml"));

//			UserGroupInformation.createUserForTesting("root", new String[] { "supergroup" });
//			UserGroupInformation.setConfiguration(conf);

			conf.set("fs.default.name", "cfs://localhost:9160/");
			cfs = FileSystem.get(conf);
//			cfs.initialize(URI.create("cfs://localhost:9160"), conf);
//			System.out.println(conf.getRaw("fs.default.name"));
//			System.out.println(cfs.g);
//
//			o = cfs.create(new Path("/testfile.txt"), true);
//			o.write(content.getBytes());
//			o.flush();
			
			FileStatus[] files = cfs.listStatus(new Path("cfs://localhost:9160/"));
			for(FileStatus file: files){
				System.out.println(file.getPath().getName());
			}

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
