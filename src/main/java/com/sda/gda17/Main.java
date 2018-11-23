package com.sda.gda17;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InvoiceDAO invoiceDAO = new InvoiceDAO();
        String pick;
        Scanner scanner = new Scanner(System.in);
        boolean condition = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Random random = new Random();

        while (condition) {
            System.out.println("Witaj w fakturowni! wybierz opcje " +
                    "\r\n 'save' aby utworzyc nowa fakture " +
                    "\r\n 'save_p' aby dodac platnika" +
                    "\r\n 'save_c' aby dodać firmę" +
                    "\r\n 'removeI' aby usunąć Fakturę" +
                    "\r\n 'removeC' aby usunąć Firmę" +
                    "\r\n 'removeP' aby usunąć Płatnika" +

                    "\r\n 'merge_pc' aby połączyć Firmę z platnikiem" +

                    "\r\n 'listFV' aby wyswietlic wszystkie faktury " +
                    "\r\n 'listP'  aby wyswietlic wszystkich platników " +

                    "\r\n 'exit' aby zakonczyc dzialanie programu");
            pick = scanner.next();
            switch (pick) {
                case "merge_pc":
                    System.out.println("Podaj ID Firmy");
                    Long idC = scanner.nextLong();
                    System.out.println("Podaj ID płatnika");
                    Long idP = scanner.nextLong();
                    invoiceDAO.mergeCompaniesWithPayers(idP, idC);
                    break;
                case "save_p":
                    System.out.println("Podaj NIP platnika");
                    String nip = scanner.next();
                    Payer payer = new Payer(null, nip, null);
                    invoiceDAO.saveSingleIntoDatabase(payer);
                    break;
                case "save":
                    LocalDate dateofIssue = LocalDate.now();
                    System.out.println("Podaj termin platnosci w formacie DD/MM/YYYY");
                    String payDate = scanner.next();
                    LocalDate paymentDate = LocalDate.parse(payDate, formatter);
                    System.out.println("Podaj kwotę faktury");
                    double amount = scanner.nextDouble();
                    System.out.println("Podaj NIP osoby wystawiajacej fakture");
                    long issuerNIP = scanner.nextLong();
                    System.out.println("Podaj NIP Platnika");
                    long payerNIP = scanner.nextLong();
                    String name = "INV" + payDate;
                    Invoice invoice = new Invoice(null, payDate, dateofIssue, paymentDate, amount, issuerNIP, payerNIP, null);
                    invoiceDAO.saveSingleIntoDatabase(invoice);
                    break;
                case "save_c":
                    System.out.println("Podaj NIP platnika");
                    String nipC = scanner.next();
                    System.out.println("Podaj nazwę firmy");
                    String nameC = scanner.next();
                    System.out.println("Podaj adres firmy");
                    String address = scanner.next();
                    Company company = new Company(null, nipC, nameC, address, null, null);
                    invoiceDAO.saveSingleIntoDatabase(company);
                    break;
                case "removeI":
                    System.out.println("Podaj ID faktury do usuniecia");
                    long invID = scanner.nextLong();
                    invoiceDAO.remove(invID,Invoice.class);
                    break;
                case "removeC":
                    System.out.println("Podaj ID firmy do usunięcia");
                    long cID = scanner.nextLong();
                    invoiceDAO.remove(cID, Company.class);
                    break;
                case "removeP":
                    System.out.println("Podaj ID Płatnika do usunięcia");
                    long pID = scanner.nextLong();
                    invoiceDAO.remove(pID,Payer.class);
                    break;
                case "list":
                    invoiceDAO.getAllInvoicesFromDatabase();
                    break;
                case "listP":
                    invoiceDAO.getAllPayersFromDatabase();
                    break;
                case "exit":
                    condition = false;
                    HibernateUtil.getSessionFactory().close();
                    break;
                default:
                    System.out.println("wybrales niedozwolona opcje, zamykanie");
                    condition = false;
                    break;
            }
        }


    }

}
