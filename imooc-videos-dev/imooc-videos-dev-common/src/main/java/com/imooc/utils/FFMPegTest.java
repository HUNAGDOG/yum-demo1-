package com.imooc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class FFMPegTest {
	private String path;
	

	public FFMPegTest(String path) {
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
		FFMPegTest test = new FFMPegTest("E:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			//test.convertor1("F:\\imooc_video_dev\\1907177ZWFKR8ARP\\video\\wxa250f029d848129e.o6zAJsyvLmFg0zQIHvdw-kA4wmF8.epcCU1w62AlC3e39adb70a0646c1e2ee1c6a61f35a26.mp4", 
			//		"F:\\imooc_video_dev\\1907177ZWFKR8ARP\\video\\temp.mp4");
			//test.convertor2("F:\\imooc_video_dev\\1907177ZWFKR8ARP\\video\\temp.mp4", "F:\\imooc_video_dev\\bgm\\gaisidewenrou.mp3", 15.0,"F:\\imooc_video_dev\\1907177ZWFKR8ARP\\video\\temp11.mp4" );
					test.convertor3("E:\\car.mp4", "E:\\123456.jpg");
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
