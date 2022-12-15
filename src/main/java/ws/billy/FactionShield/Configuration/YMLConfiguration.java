package ws.billy.FactionShield.Configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class YMLConfiguration {
	private final String _path;
	private final File _ymlfile;
	private final FileConfiguration _fc;

	/**
	 * @param name of the file. can include folder paths (for instance kits/users/Steve). Then the file Steve.yml would be in the folder plugins/'plugin_name'/kits/users/Steve.yml
	 * @param jp   -> Your plugin main instance
	 */
	public YMLConfiguration(final String name, final JavaPlugin jp) {
		this._path = "plugins/" + jp.getName();
		this._ymlfile = new File(_path + File.separatorChar + name + ".yml");
		this._fc = YamlConfiguration.loadConfiguration(this._ymlfile);
	}

	public Set<String> getKeys() {
		return getConfig().getKeys(false);
	}

	/**
	 * @param path -> path in this config
	 *
	 * @return Object if found, nullable
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T getConfigField(final String path) {
		final FileConfiguration cfg = this.getConfig();
		final Object o = cfg.get(path);
		if (o != null) {
			return (T) o;
		} else {
			cfg.set(path, "undefined");
			save();
		}
		return null;
	}

	private FileConfiguration getConfig() {
		return this._fc;
	}

	/**
	 * @return true if the save was successful, otherwise false
	 */
	private boolean save() {
		final File folder = new File(_path);
		if (! folder.exists()) {
			folder.mkdirs();
		}
		try {
			this._fc.save(this._ymlfile);
			return true;
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param path of field
	 * @param o    value as Object
	 *
	 * @return whether the save was successful
	 */
	public boolean setField(final String path, final Object o) {
		getConfig().set(path, o);
		getConfig().contains("");
		return save();
	}

	public boolean contains(final String path) {
		return getConfig().contains(path);
	}

	/**
	 * @param <T>          -> Tries to cast the field value to the Class T
	 * @param whenNotFound -> What should get created in the config if field not exist
	 * @param path         -> path in this config
	 *
	 * @return Object T if found, null if cast failed
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T getConfigField(final String path, final T whenNotFound) {
		final FileConfiguration cfg = this.getConfig();
		final Object o = cfg.get(path);
		if (o != null) {
			try {
				return (T) o;
			} catch (final Exception e) {
				return null;
			}
		} else {
			cfg.set(path, whenNotFound);
			save();
		}
		return whenNotFound;
	}
}
