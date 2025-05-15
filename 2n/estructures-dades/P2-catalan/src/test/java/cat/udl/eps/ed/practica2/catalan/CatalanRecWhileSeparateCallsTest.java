package cat.udl.eps.ed.practica2.catalan;

class CatalanRecWhileSeparateCallsTest extends AbstractCatalanTest {

    @Override
    long catalanImplementation(int n) {
        return Catalan.catalanRecWhileSeparateCalls(n);
    }
}
