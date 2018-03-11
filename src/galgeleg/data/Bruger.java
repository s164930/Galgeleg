/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galgeleg.data;

import java.io.*;
import java.util.HashMap;


public class Bruger implements Serializable
{
	// Vigtigt: Sæt versionsnummer så objekt kan læses selvom klassen er ændret!
	private static final long serialVersionUID = 12345; // bare et eller andet nr.

	public String brugernavn; // studienummer
	public String email = "hvad@ved.jeg.dk";
	public long sidstAktiv;
        public String campusnetId; // campusnet database-ID
        public String studeretning = "ukendt";
        public String fornavn = "test";
        public String efternavn = "testesen";
        public String adgangskode;
        public HashMap ekstraFelter = new HashMap();


	public String toString()
	{
		return email;
	}
}
