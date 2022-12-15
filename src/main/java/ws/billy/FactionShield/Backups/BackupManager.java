package ws.billy.FactionShield.Backups;

import org.json.JSONArray;
import ws.billy.FactionShield.FactionShield;
import ws.billy.FactionShield.Shield.Shield;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BackupManager {

	public static void saveBackup(final org.json.simple.JSONArray jsonArray, final UUID uuid) {
		try {
			Files.createDirectories(Paths.get(FactionShield.getInstance().getDataFolder().getAbsolutePath() + "/backups"));
			final FileWriter file = new FileWriter(FactionShield.getInstance().getDataFolder().getAbsolutePath() + "/backups/" + uuid.toString() + ".json");
			file.write(jsonArray.toString());
			file.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void removeBackup(final UUID uuid) {
		final File file = new File(FactionShield.getInstance().getDataFolder().getAbsolutePath() + "/backups/" + uuid.toString() + ".json");
		file.delete();
	}

	public static List<File> getBackupsList() {
		final File folder = new File(FactionShield.getInstance().getDataFolder().getAbsolutePath() + "/backups");
		final File[] listOfFiles = folder.listFiles();
		final List<File> files = new ArrayList<>();
		if (listOfFiles == null) {
			return files;
		}
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				final File file = new File(FactionShield.getInstance().getDataFolder().getAbsolutePath() + "/backups/" + listOfFiles[i].getName());
				files.add(file);
			}
		}
		return files;
	}

	public static JSONArray getBackup(final File file) throws IOException {
		final BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
		try {
			final StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			br.close();
			return new JSONArray(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			br.close();
		}
		return null;
	}

	public static List<JSONArray> getAllArrays() throws IOException {
		final List<JSONArray> jsonArrays = new ArrayList<>();
		for(File file : getBackupsList()) {
			jsonArrays.add(getBackup(file));
		}
		return jsonArrays;
	}

	public static org.json.simple.JSONArray toJson(final Shield shield) {
		final org.json.simple.JSONArray json = new org.json.simple.JSONArray();
		json.add(shield.toJson().toString());
		return json;
	}

}
