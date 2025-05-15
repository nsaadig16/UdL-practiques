package cat.udl.eps.ed.practica2.catalan;

class CatalanRecWhileTest extends AbstractCatalanTest {

    @Override
    long catalanImplementation(int n) {
        return Catalan.catalanRecWhile(n);
    }
}
