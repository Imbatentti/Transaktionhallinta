package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Asiakas;
import bean.Kirja;
import bean.Lainaus;
import bean.Nide;
import bean.NiteenLainaus;
import bean.PostinumeroAlue;

public class LainausDAO {
	static Connection yhteys = null;

	public static Connection avaaYhteys() {
		// kirjautumistiedot
		String username = "a1300789";
		String password = "xoSAJa56k";
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

	public static List<Lainaus> haeKaikkiLainaukset() throws SQLException {
		
		Connection yhteys = avaaYhteys();
		List<Lainaus> lainaukset = new ArrayList<Lainaus>();
		String sql = "SELECT Lainaus.numero, Lainaus.lainauspvm, "
				+ "Asiakas.asiakasnumero, Asiakas.etunimi, Asiakas.sukunimi, Asiakas.osoite, "
				+ "PostinumeroAlue.postinumero, PostinumeroAlue.postitmp, "
				+ "Kirja.isbn, Kirja.nimi, Kirja.kirjoittaja, Kirja.painos, Kirja.kustantaja, "
				+ "Nide.nro, "
				+ "Niteenlainaus.palautuspvm "
				+ "FROM Lainaus "
				+ "JOIN Asiakas ON Asiakas.asiakasnumero = Lainaus.numero "
				+ "JOIN PostinumeroAlue ON PostinumeroAlue.postinumero= Asiakas.postinro "
				+ "JOIN Niteenlainaus ON Lainaus.numero = Niteenlainaus.lainausnumero "
				+ "JOIN Nide ON Nide.nro = Niteenlainaus.nidenro "
				+ "JOIN Kirja ON Kirja.isbn = Nide.nideisbn;";
		Statement haku = yhteys.createStatement();
		ResultSet tulokset = haku.executeQuery(sql);
		while (tulokset.next()){
			int lainausNumero = tulokset.getInt("numero");
			Date lainausPvm = tulokset.getDate("lainauspvm");
			int asiakasnumero = tulokset.getInt("asiakasnumero");
			String etunimi = tulokset.getString("etunimi");
			String sukunimi = tulokset.getString("sukunimi");
			String osoite = tulokset.getString("osoite");
			String postinro = tulokset.getString("postinumero");
			String postitmp = tulokset.getString("postitmp");
			String isbn = tulokset.getString("isbn");
			String nimi = tulokset.getString("nimi");
			String kirjoittaja = tulokset.getString("kirjoittaja");
			int painos = tulokset.getInt("painos");
			String kustantaja = tulokset.getString("kustantaja");
			int nidenro = tulokset.getInt("nro");
			Date palautusPvm = tulokset.getDate("palautuspvm");
			Kirja kirja = new Kirja(isbn,nimi,kirjoittaja,painos,kustantaja);
			Nide nide = new Nide(kirja, nidenro);
			
			NiteenLainaus niteenlainaus = new NiteenLainaus (nide, palautusPvm);
			ArrayList<NiteenLainaus> lista = new ArrayList<NiteenLainaus>();
			lista.add(niteenlainaus);
			
			PostinumeroAlue postinumeroalue = new PostinumeroAlue(postinro, postitmp);
			Asiakas asiakas = new Asiakas(asiakasnumero, etunimi, sukunimi, osoite, postinumeroalue);
			Lainaus lainaus = new Lainaus(lainausNumero, lainausPvm, asiakas, lista);
			
			lainaukset.add(lainaus);	
		}

		return lainaukset;

	}
}
