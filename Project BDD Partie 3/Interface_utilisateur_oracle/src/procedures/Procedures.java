package procedures;

public class Procedures 
{
	public static final String acheter = "create or replace PROCEDURE Acheter\r\n" + 
			"(\r\n" + 
			"    NumCpte IN COMPTE.NumCompte%TYPE,\r\n" + 
			"    Code IN VALEUR.CodeValeur%TYPE,\r\n" + 
			"    DateA IN OPERATION.DateOp%TYPE,\r\n" + 
			"    Quant IN OPERATION.Qte%TYPE,\r\n" + 
			"    MA IN OPERATION.Montant%TYPE\r\n" + 
			")\r\n" + 
			"IS BEGIN\r\n" + 
			"    SELECT Solde INTO Variable_Solde\r\n" + 
			"    FROM OPERATION\r\n" + 
			"    WHERE NumCompte = NumCpte;\r\n" + 
			"    \r\n" + 
			"    IF Quant > 0\r\n" + 
			"    THEN\r\n" + 
			"        IF MA > 0 AND MA >= Variable_Solde\r\n" + 
			"        THEN \r\n" + 
			"            INSERT INTO OPERATION(NumCpte, DateOuverture, CodeValeur, Qte, Montant, Nature)\r\n" + 
			"            VALUES (Numero_Compte.nextval, DateA, Quant, Code, MA, 'A');\r\n" + 
			"            \r\n" + 
			"            -- MA EST DEBITÉ DU COMPTE PAR UN TRIGGER -- \r\n" + 
			"				\r\n" + 
			"            -- MISE A JOUR DE LA TABLE PORTEFEUILLE --\r\n" + 
			"			INSERT INTO PORTEFEUILLE(NumCompte, CodeValeur, Quantite, PAM, PMVL)\r\n" + 
			"            VALUES(NumCpte, Code, Quant, MA, (Cous-PAM)*Quant);\r\n" + 
			"			\r\n" + 
			"            COMMIT;\r\n" + 
			"        END IF;\r\n" + 
			"    END IF;\r\n" + 
			"END;";

	
	public static final String vendre = "CREATE OR REPLACE PROCEDURE Vendre\r\n" + 
			"(\r\n" + 
			"    NumCpte IN COMPTE.NumCompte%TYPE,\r\n" + 
			"    Code IN VALEUR.CodeValeur%TYPE,\r\n" + 
			"    DateV IN OPERATION.DateOp%TYPE,\r\n" + 
			"    Quant IN OPERATION.Qte%TYPE,\r\n" + 
			"    MV IN OPERATION.Montant%TYPE\r\n" + 
			")\r\n" + 
			"IS BEGIN\r\n" + 
			"    IF Quant > 0\r\n" + 
			"    THEN\r\n" + 
			"        IF MV > 0\r\n" + 
			"        THEN \r\n" + 
			"            INSERT INTO OPERATION(NumCompte, DateOuverture, CodeValeur, Qte, Montant, Nature)\r\n" + 
			"            VALUES (NumCpte, DateV, Quant, Code, MV, 'V');\r\n" + 
			"				\r\n" + 
			"            -- PMVR EST MIS A JOUR PAR UN TRIGGER, ON AJOUTE LE GAIN OU LA PERTE (MV - QUANT*PAM) --    \r\n" + 
			"            \r\n" + 
			"			-- MISE A JOUR DE LA TABLE PORTEFEUILLE --\r\n" + 
			"			INSERT INTO PORTEFEUILLE(NumCompte, CodeValeur, Quantite, PAM, PMVL)\r\n" + 
			"            VALUES(NumCpte, Code, Quant, MA, (Cous-PAM)*Quant);\r\n" + 
			"			\r\n" + 
			"            COMMIT;\r\n" + 
			"        END IF;\r\n" + 
			"    END IF;\r\n" + 
			"END;";
}
