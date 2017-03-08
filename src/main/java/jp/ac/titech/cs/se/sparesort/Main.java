/*
 * Copyright (c) 2010-2012 Saeki Lab. at Tokyo Institute of Technology.
 * All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.ac.titech.cs.se.sparesort;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {




//If this pathname does not denote a directory, then listFiles() returns null.


	public static void main(String[] args) throws Exception {
		String dir = args[0];
		File[] fs = new File(dir).listFiles();

		for(File f: fs){
			SequenceDatabase<String> sdb = new SequenceDatabase<String>();
			List<String> fileLines = loadStringListFromFile(f.getPath());
			int minSup = Integer.parseInt(args[1]);

			System.out.println("Started with reading");
			for (String l : fileLines) {
				sdb.addSequence(l.split(" "));
			}


			System.out.println("Done with reading");

			Map<List<String>, Integer> result = sdb.mineFrequentClosedSequences(minSup);
			try (Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(f.getName() + "_results.txt"), "utf-8"))) {

				for (Map.Entry<List<String>, Integer> entry : result.entrySet()) {
					writer.write(entry.getKey() + ":" + entry.getValue());
					writer.write("\n");
				}
			}
		}
	}

	public static List<String> loadStringListFromFile(String path)
			throws Exception {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		}
	return lines;
	}


}
