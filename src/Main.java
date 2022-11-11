import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {
    private static Stoc stoc = null;
    private static Scanner in;

    private static void printMenu() {
        System.out.println("=== Alegeti o optiune ===");
        System.out.println("1. Vizualizare produse din stoc");
        System.out.println("2. Introduceti un produs nou in lista");
        System.out.println("3. Stergere produs");
        System.out.println("4. Modificare produs");
        System.out.println("5. Creeaza comanda noua pentru produs");
        System.out.println("6. Editeaza comanda produsului");
        System.out.println("7. Vizualizare comanda produse");
        System.out.println("8. Stergere comanda produse");
        System.out.println("9. Iesire din aplicatie");
    }
//meniu aplicatie
    private static void listProducts() {
        for (Produse p : stoc.getProducts()) {
            System.out.println(p.getNume() + " : " + p.getPret());
        }
    }

    private static void addNewProduct() {
        try {
            System.out.print("Introduceti numele produslui dorit: ");
            String nume = in.nextLine();
            System.out.print("Introduceti pretul produslui dorit: ");
            double pret = Double.parseDouble(in.nextLine());
            if (stoc.addProduct(new Produse(nume, pret))) {
                System.out.println("Produsul a fost adaugat cu succes in lista!");
            } else {
                System.out.println("Produsul exista deja in lista!");
            }
        } catch (Exception e) {
            System.out.println("Date sunt invalide.");
        }
    }
//functii pentru adaugarea produsului
    private static void deleteProduct() {
        System.out.println("Introduceti numele produsului pe care doriti sa il stergeti: ");
        String nume = in.nextLine();
        if (stoc.removeProduct(nume)) {
            System.out.println("Produsul a fost sters din lista!");
        } else {
            System.out.println("Produsul nu exista in stoc!");
        }
//stergere produs
    }
    private static void initiateNewOrder() {
        System.out.println("Introduceti numele fisierului pentru comanda dorita");
    }

    public static void updateProduct() {
        System.out.println("Introduceti numele produsului pe care doriti sa il modificati: ");
        String nume = in.nextLine();
        Produse p = stoc.getProductByNume(nume);
        if (p == null) {
            System.out.println("Produsul mentionat nu exista in stoc!");
            return ;
        }
        try {
            System.out.println("Introduceti noul nume al produsului sau apasati Enter pentru a trece mai departe");
            String newNume = in.nextLine();
            System.out.println("Introduceti noul pret al produsului sau Enter pentru a trece mai departe");
            String newPretString = in.nextLine();
            double newPret = p.getPret();
            if (!newPretString.equals("")) {
                newPret = Double.parseDouble(newPretString);
            }
            if (!newNume.equals("")) {
                p.setNume(newNume);
            }
            p.setPret(newPret);
            System.out.println("Produsul a fost actualizat cu succes");
        } catch (Exception e) {
            System.out.println("Date invalide. Operatiune a esuat!");
        }
    }
//modificare produs
    private static List<ProdusComandat> readFileAndUpdateOrder(String filenume) {
        try {
            List<ProdusComandat> items = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(filenume));
            String currentLine = reader.readLine();
            while (currentLine != null) {
                if (currentLine.equals("")) {
                    currentLine = reader.readLine();
                    continue;
                }
                String[] data = currentLine.split(";");
                if (data.length != 2) {
                    System.out.println("Date incorecte in fisier. Un rand are forma <nume_produs>;<cantitate>");
                    reader.close();
                    return null;
                }
                String nume = data[0];
                int quantity = Integer.parseInt(data[1]);
                Produse p = stoc.getProductByNume(nume);
                if (p == null) {
                    System.out.println("Date incorecte. Nu exista produsul cu numele " + nume + ". Operatiune terminata.");
                    reader.close();
                    return null;
                }
                items.add(new ProdusComandat(p, quantity));
                currentLine = reader.readLine();
            }
            reader.close();
            return items;
        } catch (Exception e) {
            System.out.println("A aparut o eroare in procesarea fisierului " + filenume + ".");
        }
        return null;
    }
//listare produse
    private static void createNewOrder() {
        System.out.print("Introduceti numele comenzii: ");
        String filenume = in.nextLine();
        if (stoc.getOrderByNume(filenume) != null) {
            System.out.println("Comanda cu numele dat exista deja!");
            return ;
        }
        Comanda newOrder = new Comanda(filenume);
        try {
            String txtFilenume = filenume + ".txt";
            File orderFile = new File(txtFilenume);
            if (orderFile.createNewFile()) {
                Runtime rs = Runtime.getRuntime();
                Process p = rs.exec("notepad " + txtFilenume);
                p.waitFor();
            }
            List<ProdusComandat> produsComandat = readFileAndUpdateOrder(txtFilenume);
            if (produsComandat != null) {
                newOrder.setItems(produsComandat);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("O eroare a aparut in crearea sau editarea fisierului.");
        }
        stoc.addOrder(newOrder);
    }
// creare comanda
    private static void updateOrder() {
        System.out.print("Introduceti numele comenzii: ");
        String filenume = in.nextLine();
        Comanda order = stoc.getOrderByNume(filenume);
        if (order == null) {
            System.out.println("Comanda cu numele dat nu exista!");
            return ;
        }
        try {
            String txtFilenume = order.getFilenume() + ".txt";
            Runtime rs = Runtime.getRuntime();
            Process p = rs.exec("notepad " + txtFilenume);
            p.waitFor();

            List<ProdusComandat> produsComandat = readFileAndUpdateOrder(txtFilenume);
            if (produsComandat != null) {
                order.setItems(produsComandat);
            }
        } catch (Exception e) {
            System.out.println("O eroare a aparut in editarea fisierului.");
        }
    }
    //modofica comanda
    private static void viewOrder() {
        System.out.print("Introduceti numele comenzii: ");
        String filenume = in.nextLine();
        Comanda order = stoc.getOrderByNume(filenume);
        if (order == null) {
            System.out.println("Comanda cu numele dat nu exista!");
            return ;
        }
        for (ProdusComandat item : order.getItems()) {
            System.out.println(item.getCantitate() + " x " + item.getProduct().getNume() + "\t\t" + item.getCantitate() * item.getProduct().getPret());
        }
    }
    // vizualizare comanda
    private static void deleteOrder() {
        System.out.print("Introduceti comanda pe care doriti sa o stergeti: ");
        String filenume = in.nextLine();
        Comanda order = stoc.getOrderByNume(filenume);
        if (order == null) {
            System.out.println("Comanda cu numele dat nu exista!");
            return;
        }
        String txtFilenume = null;
        try {
            txtFilenume = order.getFilenume() + ".txt";
            File f = new File(txtFilenume);
            if (f.delete()) {
                System.out.println("Comanda a fost stearsa cu succes!");
                stoc.removeOrder(order);
            } else {
                System.out.println("Nu s-a putut sterge fisierul comenzii " + txtFilenume);
            }
        } catch (Exception e) {
            System.out.println("Nu s-a putut sterge fisierul comenzii " + txtFilenume);
            return;
        }
    }
//stergere comanda
    public static void main(String[] args) {
        try {
            FileInputStream fileIn = new FileInputStream("stoc.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            stoc = (Stoc) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            stoc = new Stoc();
        } catch (ClassNotFoundException e) {
            System.out.println("Nu s-a putut gasi clasa Stoc");
            return ;
        }
        in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int option = Integer.parseInt(in.nextLine());
                switch (option) {
                    case 1: listProducts(); break;
                    case 2: addNewProduct(); break;
                    case 3: deleteProduct(); break;
                    case 4: updateProduct(); break;
                    case 5: createNewOrder(); break;
                    case 6: updateOrder(); break;
                    case 7: viewOrder(); break;
                    case 8: deleteOrder(); break;
                    case 9: running = false; break;
                    default: System.out.println("Comanda invalida. Valorile valide sunt valorile intre 1-8"); break;
                }
            } catch (Exception e) {
                System.out.println("Comanda invalida. Valorile valide sunt valorile intre 1-5");
            }
        }
        try {
            File serializationFile = new File("stoc.ser");
            serializationFile.createNewFile();
            FileOutputStream fileOut =
                    new FileOutputStream("stoc.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(stoc);
            out.close();
            fileOut.close();
            System.out.printf("Datele serializate sunt salvate in fisierul /stoc.ser");
        } catch (IOException i) {
            System.out.println("Eroare in scrierea fisierului de serializare");
        }
    }
}
