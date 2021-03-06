package logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import information.RunningProgramInformation;
import ownLibaries.FileLibary.OwnFileWriter;

/**
 * 
 * @author FrederikFJ
 * @since version 0.0.1
 *
 */
public class Logger {
	
	
	
	
	private static final String infoLogFilePath =  RunningProgramInformation.runningPath + "logs/info.log";
	private static final File infoLogFile = new File(infoLogFilePath);
	
	
	private static final String errorLogFilePath = RunningProgramInformation.runningPath +  "logs/error.log";
	private static final File errorLogFile = new File(errorLogFilePath);

	private static final String allLogFilePath = RunningProgramInformation.runningPath + "logs/logs.log";
	private static final File logFile = new File(allLogFilePath);
		
	/**
	 * Logs in the Console and in a File
	 * Example for a log:<br>
	 * 21.04.2020 10:07:40 [Program][Shutdown] --> The SmartHome program is shutting down...<br>
	 * 
	 * 
	 * 
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'Program')
	 * @param type Shown type of the information (In the example is the type 'Shutdown')
	 * @param output Shown information (In the example is the output 'The SmartHome is shutting down...')
	 */
	public static void log(String source, String type, String output) {
		
		StringBuilder logOutput = new StringBuilder();
		
		checkLogFile(logFile);
		
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput.append(formatDateTime)
					.append(" [")
					.append(source)
					.append("][")
					.append(type)
					.append("] --> ")
					.append(s)
					.append("\n");
		}
		
		OwnFileWriter.add(infoLogFile, logOutput.substring(0, logOutput.length()-1));
		OwnFileWriter.add(logFile, logOutput.substring(0, logOutput.length()-1));
		System.out.print(logOutput);
		
		
		
	}
	
	
	/**
	 * Logs in the Console
	 * Example for a log:<br>
	 * 21.04.2020 10:07:40 [Program][Shutdown] --> The SmartHome program is shutting down...<br>
	 * 
	 * 
	 * 
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'Program')
	 * @param type Shown type of the information (In the example is the type 'Shutdown')
	 * @param output Shown information (In the example is the output 'The SmartHome is shutting down...')
	 */
	public static void logConsole(String source, String type, String output) {
		
		StringBuilder logOutput = new StringBuilder();
		
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput.append(formatDateTime)
					.append(" [")
					.append(source)
					.append("][")
					.append(type)
					.append("] --> ")
					.append(s)
					.append("\n");
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
	 * @since version 0.0.1
	 * 
	 * @param source Shown source from the information (In the example is the source 'FileWriter')
	 * @param output Shown information (In the example is the output 'File /home/pi/Documents/smarthome/code/java/bin/../.././logs/test.log couldn't be found')
	 */
	public static void logError(String source, String output) {
		
		StringBuilder logOutput = new StringBuilder();
		
		checkLogFile(errorLogFile);
		
		//Datum + Zeit bekommen & formatieren
		LocalDateTime dt = LocalDateTime.now();
		DateTimeFormatter dtformater= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		String formatDateTime = dt.format(dtformater);
		
		
		// Bilden des Outputs
		String[] OutputArray = output.split("\n");
		for(String s : OutputArray) {
			logOutput
					.append(formatDateTime)
					.append(" [")
					.append(source)
					.append("][")
					.append("Error")
					.append("] --> ")
					.append(s)
					.append("\n");
		}
		
		OwnFileWriter.add(errorLogFile, logOutput.substring(0, logOutput.length()-1));
		OwnFileWriter.add(logFile, logOutput.substring(0, logOutput.length()-1));
		System.err.print(logOutput);
		
	}


	/**
	 * @since version 0.0.3
	 * @param file file which should be created
	 */
	private static void createLogFile(File file) {
		OwnFileWriter.createFile(file);
	}
	
	
	/**
	 * Method which create a Log file if the log file is non-existent and renames LogFiles if the Log-File is to large
	 * 
	 * @since version 0.0.3
	 * @param file File which should be checked
	 */
	private static void checkLogFile(File file) {
		if(file.exists()) {
			// gr??e eine log file beim archivieren
			if(file.length() < 5* 1000*1000) return;
			Logger.changeLogFile(file);
			return;
		}
		createLogFile(file);
	}
	
	/**
	 * renames the logFile
	 * 
	 * @since version 0.0.3
	 * @param file file which should be renamed
	 */
	private static void changeLogFile(File file) {
		boolean bool = false;
		int i = 1;
		while(!bool) {
			bool = OwnFileWriter.renameFile(file, new File(file.getPath() + i));
			i++;
		}
		
	}
	

	
	
}
