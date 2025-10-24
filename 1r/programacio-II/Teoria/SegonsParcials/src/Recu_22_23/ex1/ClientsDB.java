package Recu_22_23.ex1;

import java.io.*;

public class ClientsDB {
    private RandomAccessFile raf;

    public ClientsDB(String fname) throws IOException {
        this.raf = new RandomAccessFile(fname, "rw");
    }

    public void close() throws IOException {
        this.raf.close();
    }

    /**
     * (l.26) Com que els clients començen a partir de 1L, hem de restar 1 per no deixar espais.
     * <p>
     * (l.28) El fromBytes d'abans ja s'ocupa del total
     * @param id id del Client
     * @return el client
     * @throws IOException
     */
    public Client read(long id) throws IOException {
        byte[] record = new byte[Client.SIZE];
        this.raf.seek((id - 1L) * Client.SIZE );
        this.raf.read(record);
        return Client.fromBytes(record);
    }

    public void write(Client client) throws IOException {
        byte[] record = new byte[Client.SIZE];
        this.raf.seek((client.getId() - 1L) * Client.SIZE);
        this.raf.write(record);
    }
    public long numClients() throws IOException {
        return this.raf.length() / Client.SIZE;
    }
}

