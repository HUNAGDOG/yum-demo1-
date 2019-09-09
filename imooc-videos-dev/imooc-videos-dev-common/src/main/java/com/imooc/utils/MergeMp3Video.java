package com.imooc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class MergeMp3Video {
	private String path;
	

	public MergeMp3Video(String path) {
		super();
		this.path = path;
	}
	public  void convertor1(String inputPath , String outputPath) throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(path);
		command.add("-i");
		command.add(inputPath);
		command.add("-vcodec");
		command.add("copy");
		command.add("-an");
		command.add(outputPath);
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader reader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while((line = br.readLine()) != null) {
			
		}
		if(br != null) {
			br.close();
		}
		if(errorStream != null) {
			errorStream.close();
		}
		if(reader != null) {
			reader.close();
		}
		File file = new File(inputPath);
		file.delete();
	}
	public void convertor2(String videoPath, String Mp3Path, double secondes,String outpuPath)throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(path);
		
		command.add("-i");
		command.add(videoPath);
		
		command.add("-i");
		command.add(Mp3Path);
		
		command.add("-t");
		command.add(String.valueOf(secondes));
		
		command.add("-y");
		command.add(outpuPath);
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader reader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while((line = br.readLine()) != null) {
			
		}
		if(br != null) {
			br.close();
		}
		if(errorStream != null) {
			errorStream.close();
		}
		if(reader != null) {
			reader.close();
		}
		File file = new File(videoPath);
		file.delete();
		
	}
	public  void convertor3(String inputPath , String outputPath) throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(path);
		command.add("-ss");
		command.add("00:00:01");
		command.add("-y");
		command.add("-i");
		command.add(inputPath);
		command.add("-vframes");
		command.add("1");
		command.add(outputPath);
		command.add(outputPath);
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Process process = processBuilder.start();
		InputStream errorStream = process.getErrorStream();
		InputStreamReader reader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while((line = br.readLine()) != null) {
			
		}
		if(br != null) {
			br.close();
		}
		if(errorStream != null) {
			errorStream.close();
		}
		if(reader != null) {
			reader.close();
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MergeMp3Video test = new MergeMp3Video("E:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			test.convertor1("E:\\car.mp4", "E:\\car123.mp4");
			test.convertor2("E:\\car123.mp4", "E:\\gaisidewenrou.mp3", 15.0,"E:\\car123456.mp4" );
			test.convertor3("E:\\car123456.mp4", "E:\\\\car123456.jpg");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
