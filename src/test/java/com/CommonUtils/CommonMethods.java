package com.CommonUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommonMethods {
		public static void runTerminalCommand() {
		try {
			String path = System.getProperty("user.dir");
			System.out.println("Setting up the docker container ... ");
			ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c","cd \"C:\\Program Files\\Docker\\Docker\"&& docker-compose -f"+ System.getProperty("user.dir")+"\\docker-compose.yml up -d");
			
			builder.redirectErrorStream(true);
			Process p = builder.start();
			BufferedReader r= new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = r.readLine();
			while(line!=null) {
				Thread.sleep(1000);
				line = r.readLine();
			}
			System.out.println("Done");
		} catch (Exception e ) {
			e.printStackTrace();
		}
			
		
	}
		
		public static void killTerminalCommand() {
			try {
				String path = System.getProperty("user.dir");
				System.out.println("Removing the docker container ... ");
				ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c","cd \"C:\\Program Files\\Docker\\Docker\"&& docker-compose -f"+ System.getProperty("user.dir")+"\\docker-compose.yml down");
				
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r= new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line = r.readLine();
				while(line!=null) {
					Thread.sleep(1000);
					line = r.readLine();
				}
				System.out.println("Done");
			} catch (Exception e ) {
				e.printStackTrace();
			}
				
			
		}
	
 
}
