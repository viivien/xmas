package charco.xmas.domain;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

public class PartieTest {

    @Test
    public void testNewPartie_shouldHavePushTour() {
        Partie maPartie = new Partie();

        assertEquals(maPartie.currentTurn(), TourDeJeu.PUSH);
    }
}
