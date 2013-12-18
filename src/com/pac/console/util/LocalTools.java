package com.pac.console.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Get prop grabberer
 * 
 * @author pvyParts
 *
 */
public class LocalTools {
	public static String getProp(String propKey) {
		Process p = null;
		String propVal = "";
		try {
			p = new ProcessBuilder("/system/bin/getprop", propKey)
					.redirectErrorStream(true).start();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				propVal = line;
			}
			p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propVal;
	}

}
