package logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import ownLibaries.FileLibary.OwnFileWriter;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.1
 *
 */
public class Logger {
	
	private static String LogFilePath = "../../logs/infos.log";
	private static File logFile = new File(LogFilePath);
	
	private static String errorLogFilePath = "../../logs/error.log";
	private static File errorLogFile = new File(errorLogFilePath);
	
	/**
	 * Logs in the Console and in a File
	 * Example for a log:<br>
	 * 21.04.2020 10:07:40 [Program][Shutdown] --> The SmartHome program is shutting down...<br>
	 * 
	 * 
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'Program')
	 * @param type Shown type of the information (In the example is the type 'Shutdown')
	 * @param output Shown information (In the example is the output 'The SmartHome is shutting down...')
	 */
	public static void log(String source, String type, String output) {
		
		String logOutput = "";
		
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput += formatDateTime + " [" + source + "][" + type + "] --> " + s + "\n";
		}
		
		OwnFileWriter.addLine(logFile, logOutput.substring(0, logOutput.length()-1));
		System.out.print(logOutput);
		
		
		
	}
	
	
	/**
	 * Logs in the Console
	 * Example for a log:<br>
	 * 21.04.2020 10:07:40 [Program][Shutdown] --> The SmartHome program is shutting down...<br>
	 * 
	 * 
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'Program')
	 * @param type Shown type of the information (In the example is the type 'Shutdown')
	 * @param output Shown information (In the example is the output 'The SmartHome is shutting down...')
	 */
	public static void logConsole(String source, String type, String output) {
		
		String logOutput = "";
		
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput += formatDateTime + " [" + source + "][" + type + "] --> " + s + "\n";
		}
		
		System.out.print(logOutput);

	}

	
	/**
	 * Logs in the Console and in an <b>extra</b> file for errors
	 * Example for a log:<br>
	 * 20.04.2020 19:55:25 [FileWriter][Error] --> File /home/pi/Documents/smarthome/code/java/bin/../.././logs/test.log couldn't be found<br>
	 * 
	 * 
	 * 
	 * @author FrederikFJ
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'FileWriter')
	 * @param output Shown information (In the example is the output 'File /home/pi/Documents/smarthome/code/java/bin/../.././logs/test.log couldn't be found')
	 */
	public static void logError(String source, String output) {
		
		String logOutput = "";
		
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput += formatDateTime + " [" + source + "][" + "Error" + "] --> " + s + "\n";
		}
		
		OwnFileWriter.addLine(errorLogFile, logOutput.substring(0, logOutput.length()-1));
		System.out.print(logOutput);
		
	}
}
