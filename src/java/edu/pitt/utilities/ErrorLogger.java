

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ErrorLogger {

	public static void log(String errorDescription) {
		File file = new File("/Users/michaelsloan/Desktop/Spring Semester 2014/Design of Info Systems/logs/" + StringUtilities.dateToFilename("txt"));
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println(String.valueOf(new Date()) + "|" + errorDescription);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
