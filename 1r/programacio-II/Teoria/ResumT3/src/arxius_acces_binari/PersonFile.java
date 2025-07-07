package arxius_acces_binari;

import java.io.IOException;
import java.io.RandomAccessFile;

public class PersonFile {

    private RandomAccessFile people;

    /**
     * El constructor inicialitza el RandomAccessFile amb el nom del fitxer i el mode: lectura ("r") o lectura i escriptura ("rw")
     *
     * @param file El fitxer que entra
     * @throws IOException
     */
    public PersonFile (String file) throws IOException {
        this.people = new RandomAccessFile(file,"rw");
    }

    /**
     * Llegeix una Person del fitxer
     * <p>
     *     La persona està a la posició id * Person.SIZE
     *     <p>
     *  Guardem la persona en un registre (array de bytes)
     *  <p>
     *      Transformem el registre en una persona amb fromBytes (de la classe Person)
     * @param id identificador de la Person
     * @return Persona amb la id a l'arxiu "people"
     */
    public Person readPerson(long id) throws IOException{
        people.seek(id * Person.SIZE);
        byte[] record = new byte[Person.SIZE];
        people.read(record);
        return Person.fromBytes(record);
    }

    /**
     * Busquem la posició de la Person, la passem a bytes i la guardem allà
     * @param person La persona que volem escriure
     * @throws IOException
     */
    public void writePerson (Person person) throws IOException{
        people.seek(person.getId() * Person.SIZE);
        byte[] record = person.toBytes();
        people.write(record);
    }

    /**
     * setLength canvia la llargada del fitxer
     * @param newLength nova llargada <p>
     *                  Si és 0, borra l'arxiu
     * @throws IOException
     */
    public void changeLength (long newLength) throws  IOException{
        people.setLength(newLength);
    }





}
