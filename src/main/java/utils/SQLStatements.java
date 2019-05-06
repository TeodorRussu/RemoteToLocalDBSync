package utils;

/**
 * SQL statements and queries
 */
public class SQLStatements {
    public static final String SIMPLE_INSERT_TO_CLIENTI = "INSERT INTO clienti (denumire, adresa, localitate, judet, tara) VALUES (?,?,?,?,?)";
    public static final String SELECT_CLIENTS_FROM_LOCAL = "SELECT denumire, adresa, localitate, judet, tara FROM clienti";
    public static final String SELECT_CLIENTS_FROM_REMOTE= "SELECT payment_firstname, payment_lastname, payment_company, payment_address_1, payment_address_2, payment_city,payment_zone, payment_postcode, payment_country FROM %s WHERE order_status_id != 0";
}
