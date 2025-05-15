package Examen_21_22.ex1;
import acm.program.CommandLineProgram;
import java.io.*;
import java.util.StringTokenizer;

public class StockUpdater extends CommandLineProgram {

    private static final String CATALOG = "catalog.dat";
    private static final String OPERATIONS = "operations.csv";
    private Catalog catalog;
    private BufferedReader operations;

    public void run() {
        try {
            openFiles();
            processMovements();
            closeFiles();
        } catch (IOException ex) {
            println("Error");
        }
    }

    private void openFiles() throws IOException {
        catalog = new Catalog(CATALOG);
        operations = new BufferedReader(new FileReader(OPERATIONS));
    }

    private void closeFiles() throws IOException {
        catalog.close(); operations.close();
    }

    private void processMovements() throws IOException {
        String order = operations.readLine();
        while (order != null){
            processMovement(order);
            order = operations.readLine();
        }
    }

    private void processMovement(String order) throws IOException{
        StringTokenizer st = new StringTokenizer(order,",");
        while (st.hasMoreTokens()){
            String operation = st.nextToken();
            String parameter1 = st.nextToken();
            String parameter2 = st.nextToken();
            if(operation.equals("ALTA")){
                int initialStock = Integer.parseInt(parameter2);
                processNewProduct(parameter1,initialStock);
            } else if (operation.equals("VENTA")) {
                long id = Long.parseLong(parameter1);
                int units = Integer.parseInt(parameter2);
                processSale(id,units);
            }
        }
    }

    private void processNewProduct(String name, int initialStock) throws IOException{
        if(initialStock > 0){
            long id = catalog.nextId();
            Product product = new Product(id,name);
            catalog.write(product);
        }
    }

    private void processSale(long id, int units) throws IOException{
        if(catalog.contains(id) && units > 0){
            Product product = catalog.read(id);
            product.decrementUnits(units);
        }
    }

    public static void main(String[] args) {
        new StockUpdater().start(args);
    }
}
