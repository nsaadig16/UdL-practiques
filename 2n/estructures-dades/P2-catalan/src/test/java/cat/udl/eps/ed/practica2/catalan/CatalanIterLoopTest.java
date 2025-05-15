package cat.udl.eps.ed.practica2.catalan;

class CatalanIterLoopTest extends AbstractCatalanTest {

    @Override
    long catalanImplementation(int n) {
        return Catalan.catalanIter(n);
    }
}
