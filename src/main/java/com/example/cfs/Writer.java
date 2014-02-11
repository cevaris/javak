package com.example.cfs;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class Writer {
	
	public static void main(String[] args) throws Exception {

		FSDataOutputStream o = null;
		FileSystem cfs = null;

		try {
			System.setProperty("cassandra.config", ClassLoader.getSystemResource("conf/cassandra.yaml").toString());
			System.setProperty("dse.config", ClassLoader.getSystemResource("conf/dse.yaml").toString());

			Configuration conf = new Configuration();
			conf.addResource(new Path("/usr/local/dse/resources/hadoop/conf/core-site.xml"));


			conf.set("fs.default.name", "cfs://localhost:9160/");
			cfs = FileSystem.get(conf);

			BufferedWriter br=new BufferedWriter(new OutputStreamWriter(cfs.append(new Path("/testfile.txt"))));
			int count = 50;
			while(count-- > 0){
				String line = UUID.randomUUID().toString();
				System.out.println(line);
				br.write(line+"\n");
			}
			br.close();
			
			FileStatus[] files = cfs.listStatus(new Path("cfs://localhost:9160/"));
			for(FileStatus file: files){
				System.out.println(file.getPath().getName() + " "+ file.getLen() + " "+ file.isDir());
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
