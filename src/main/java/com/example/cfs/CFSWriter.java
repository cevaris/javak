package com.example.cfs;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public class CFSWriter {
	
		
	
	Configuration conf = null;
	FSDataOutputStream o = null;
	FileSystem cfs = null;
	BufferedWriter bw = null;
	FSDataOutputStream fsOut = null;

	public CFSWriter() {

		System.setProperty("cassandra.config",
				ClassLoader.getSystemResource("conf/cassandra.yaml").toString());
		System.setProperty("dse.config",
				ClassLoader.getSystemResource("conf/dse.yaml").toString());

		
		this.conf = new Configuration();
		this.conf.addResource(new Path(
				"/usr/local/dse/resources/hadoop/conf/core-site.xml"));
		
		Path path = new Path("/2014-2-11-9");

		try {
			this.conf.set("fs.default.name", "cfs://localhost:9160/");
			this.cfs = FileSystem.get(conf);
			this.cfs.createNewFile(path);
			this.fsOut = cfs.append(path,4048);
			System.out.println(String.format("Path %s exists? %s", path, this.cfs.exists(path)));
			this.bw = new BufferedWriter(new OutputStreamWriter(fsOut),4048);
		} catch (JsonMappingException err) {
			err.printStackTrace();
		} catch (JsonGenerationException err) {
			err.printStackTrace();
		} catch (IOException err) {
			err.printStackTrace();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.print("Closing CFS Writer");
				close();
				System.out.println("...Closed CFS Writer");
			}
		});
		
		
	}
	
	public void append(String line){
		
		try {

			System.out.println(String.format("Writing: %s",line ));
			this.bw.write(line);
			this.bw.newLine();
			this.bw.flush();

		} catch (JsonMappingException err) {
			err.printStackTrace();
		} catch (JsonGenerationException err) {
			err.printStackTrace();
		} catch (IOException err) {
			err.printStackTrace();
		}
		
	}

	public void close() {

		try {
			this.bw.flush();
			this.bw.close();
			this.cfs.close();
			this.fsOut.close();
		} catch (IOException err) {
			err.printStackTrace();
		}

	}
}
