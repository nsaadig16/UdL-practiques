package Recu_22_23.ex1;
import java.io.*;
import java.util.StringTokenizer;

import acm.program.*;

public class Updater extends ConsoleProgram {
    private static final String CLIENTS = "clients.dat";
    private static final String PRODUCTS = "products.dat";
    private static final String PURCHASES = "purchases.txt";
    private ClientsDB clientsDB;
    private ProductsDB productsDB;
    private BufferedReader purchases;
    public void run() {
        try {
            openFiles();
            processPurchases();
            closeFiles();
        } catch (IOException ex) {
            println("Ha habido un error");
        }
    }
    private void openFiles() throws IOException {
        clientsDB = new ClientsDB(CLIENTS);
        productsDB = new ProductsDB(PRODUCTS);
        purchases = new BufferedReader(new FileReader(PURCHASES));
    }


    private void closeFiles() throws IOException {
        clientsDB.close(); productsDB.close(); purchases.close();
    }

    /**
     * (l.44) Primer vigilem que el producte existeixi i la quantitat sigui positiva.
     * <p>
     * (l.45) Llegim el producte de la base de dades amb el id
     * <p>
     * (l.48) Obtenim el preu del producte
     * <p>
     * (l.49) Li sumem la seva compra amb el add
     * <p>
     * (l.55) IMPORTANT! Al acabar tornem a escriure al client a la base de dades.
     * @param client el client
     * @param idProduct la id del producte
     * @param quantity la quantitat que ha comprat
     * @throws IOException
     */
    private void updateClient(Client client, long idProduct, int quantity) throws IOException {
        if(productExists(idProduct) && quantity > 0) {
            Product product = this.productsDB.read(idProduct);
            int price = product.getPrice();
            client.add(quantity, price);
            this.clientsDB.write(client); //IMPORTANT
        }
    }

    /**
     * Anem llegint línies amb el reader i les processem una a una
     * @throws IOException
     */
    private void processPurchases() throws IOException {
        String line = this.purchases.readLine();
        while(line != null){
            processPurchase(line);
            line = this.purchases.readLine();
        }
    }

    /**
     * (84) Tokenitzem per ";"
     * <p>
     * (85) Transformem el primer token (el idClient) en long
     * <p>
     * (86) Comprovem que existeixi el client
     * <p>
     * (87-90) Anem repassant tots els tokens, transformant el primer en producte i el segon en quantitat.
     * <p>
     * (91)Els afegim amb updateClient
     * @param line Una línia amb totes les compres d'un client
     * @throws IOException
     */
    private void processPurchase(String line) throws IOException{
        StringTokenizer tokens = new StringTokenizer(line,";");
        long idClient = Long.parseLong(tokens.nextToken());
        if(clientExists(idClient)){
            while(tokens.hasMoreTokens()){
                Client client = clientsDB.read(idClient);
                long idProduct = Long.parseLong(tokens.nextToken());
                int quantity = Integer.parseInt(tokens.nextToken());
                updateClient(client,idProduct,quantity);
            }
        }
    }

    /**
     * Mirem si el client existeix
     * @throws IOException
     */
    private boolean clientExists (long id) throws IOException {
        return 1L <= id && id <= clientsDB.numClients();
    }

    /**
     * Mirem si el producte existeix
     * @throws IOException
     */
    private boolean productExists (long id) throws  IOException{
        return 0L <= id && id <= productsDB.numProducts();
    }
}

