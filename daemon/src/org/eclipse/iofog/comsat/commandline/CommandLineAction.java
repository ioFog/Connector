package org.eclipse.iofog.comsat.commandline;

import org.eclipse.iofog.comsat.ComSat;
import org.eclipse.iofog.comsat.utils.Constants;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public enum CommandLineAction {

	STOP_ACTION {
		@Override
		public List<String> getKeys() {
			return singletonList("stop");
		}

		@Override
		public String perform(String params) {
			synchronized (ComSat.exitLock) {
				ComSat.exitLock.notifyAll();
			}
			return "ComSat stopped... :)";
		}
	},
	STATUS_ACTION {
		@Override
		public List<String> getKeys() {
			return singletonList("status");
		}

		@Override
		public String perform(String params) {
			return "Comsat is up and running.";
		}
	},
	HELP_ACTION {
		@Override
		public List<String> getKeys() {
			return asList("help", "--help", "-h", "-?");
		}

		@Override
		public String perform(String params) {
			return CommandLineAction.showHelp();
		}
	},
	VERSION_ACTION {
		@Override
		public List<String> getKeys() {
			return asList("version", "--version", "-v");
		}

		@Override
		public String perform(String params) {
			return CommandLineAction.showVersion();
		}
	};

	public abstract List<String> getKeys();

	public abstract String perform(String params);

	public static CommandLineAction getActionByKey(String cmdKey) {
		return Arrays.stream(values())
				.filter(action -> action.getKeys().contains(cmdKey))
				.findAny()
				.orElse(HELP_ACTION);
	}

	public static String showHelp() {

		return ("Usage: comsat [OPTION]\n\n"
				+ "Option           GNU long option         Meaning\n"
				+ "======           ===============         =======\n"
				+ "-h, -?           --help                  Show this message\n"
				+ "-v               --version               Display the software version and\n"
				+ "                                         license information\n" + "\n" + "\n"
				+ "Command          Arguments               Meaning\n"
				+ "=======          =========               =======\n"
				+ "help                                     Show this message\n"
				+ "version                                  Display the software version and\n"
				+ "                                         license information\n"
				+ "status                                   Display current status information\n"
				+ "                                         about the software\n"
				+ "Report bugs to: edgemaster@iofog.org\n" + "ioFog home page: http://iofog.org\n"
				+ "For users with Eclipse accounts, report bugs to: https://bugs.eclipse.org/bugs/enter_bug.cgi?product=iofog");
	}

	public static String showVersion() {
		return "Comsat " + Constants.VERSION +
				"\nCopyright (C) 2018 Edgeworx, Inc." +
				"\nEclipse ioFog is provided under the Eclipse Public License (EPL2)" +
				"\nhttps://www.eclipse.org/legal/epl-v20.html";
	}
}