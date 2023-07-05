// package Hadoop;

// import java.io.*;
// import java.util.*;
// import org.apache.hadoop.fs.*;
// import org.apache.hadoop.io.*;
// import org.apache.hadoop.mapred.*;
package wc3;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class WC
{

	public static class Map extends MapReduceBase implements
	 Mapper<LongWritable, Text, Text, IntWritable>
	{
		private final static IntWritable one=new IntWritable(1);
	

		@Override
		public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> out, Reporter arg3)
				throws IOException 
		{
			// TODO Auto-generated method stub
			//String myvalue=value.toString();
			String line=value.toString();
			String[] data=line.split(",");
//			out.collect(new Text(data[2]), one);
			out.collect(new Text(data[2]), new IntWritable(Integer.parseInt(data[3])));
//			StringTokenizer token=new StringTokenizer(myvalue);
//			while(token.hasMoreTokens()) {
//				word.set(token.nextToken());
//				out.collect(word, one);
//			}
			
		}
		
		
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
	{

		@Override
		public void reduce(Text key, Iterator<IntWritable> value, OutputCollector<Text, IntWritable> out,
				Reporter arg3) throws IOException 
		{
			// TODO Auto-generated method stub
			int sum=0;
			while(value.hasNext()) {
				sum+=value.next().get();
			}
			out.collect(key, new IntWritable(sum));
		}
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JobConf conf =new JobConf(WC.class);
		conf.setJobName("WC");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		conf.setMapperClass(Map.class);
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);
		
				
		
		
		
	}

}


public class Lab4b {
	
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
	{
		private final static IntWritable one = new IntWritable(1);
		
		public void map (LongWritable key, Text value, OutputCollector<Text,IntWritable> output, Reporter reporter) throws IOException {
			String valueString = value.toString();
			String[] data = valueString.split(",");
			output.collect(new Text(data[2]),new IntWritable(Integer.parseInt(data[3])));
		}
	}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
	{

		public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
	

			int tcount = 0;
			while(values.hasNext())
			{
				IntWritable value = (IntWritable) values.next();
				tcount += value.get();
			}
			
			output.collect(key, new IntWritable(tcount));
			
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JobConf conf = new JobConf(Lab4b.class);
		conf.setJobName("BankTransaction");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		
		conf.setMapperClass(Map.class);
		
		conf.setCombinerClass(Reduce.class);
		conf.setReducerClass(Reduce.class);
		
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		JobClient.runJob(conf);

	}

}
