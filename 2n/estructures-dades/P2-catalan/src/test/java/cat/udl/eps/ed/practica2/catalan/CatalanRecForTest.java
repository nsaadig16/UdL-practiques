package cat.udl.eps.ed.practica2.catalan;

class CatalanRecForTest extends AbstractCatalanTest {

    @Override
    long catalanImplementation(int n) {
        return Catalan.catalanRecFor(n);
    }
}
