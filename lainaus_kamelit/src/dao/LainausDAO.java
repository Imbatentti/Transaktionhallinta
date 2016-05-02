package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class LainausDAO {
	static Connection yhteys = null;

	public static Connection avaaYhteys() {
		// kirjautumistiedot
		String username = "a1300789";
		String password = "xoSAja56k";
		String url = "jdbc:mariadb://localhost/a1300789";

		try {
			// YHTEYDEN AVAUS
			// ajurin lataus
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			// avataan yhteys
			yhteys = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			// JOTAIN VIRHETTÄ TAPAHTUI
			e.printStackTrace();
			System.out.println("Tietokantahaku aiheutti virheen");
		}
		return yhteys;

	}

	public static void suljeYhteys() {
		try {
			if (yhteys != null && !yhteys.isClosed())
				yhteys.close();
		} catch (Exception e) {
			System.out
					.println("Tietokantayhteys ei jostain syystä suostu menemään kiinni.");
		}
	}
}
