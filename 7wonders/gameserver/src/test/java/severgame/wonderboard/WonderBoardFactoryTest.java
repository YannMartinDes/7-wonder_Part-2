package severgame.wonderboard;

import commun.material.MaterialType;
import commun.wonderboard.WonderBoard;
import org.junit.jupiter.api.Test;
import servergame.wonderboard.WonderBoardFactory;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WonderBoardFactoryTest {

    WonderBoardFactory wonderBoardFactory = new WonderBoardFactory();

    @Test
    void createWonderBoardTest(){
        ArrayList<WonderBoard> wonderBoards = wonderBoardFactory.createWonderBoard();

        assertEquals("Le Colosse de Rhodes",wonderBoards.get(0).getWonderName());
        assertEquals("Le Phare d'Alexandrie",wonderBoards.get(1).getWonderName());
        assertEquals("Le temple d'Artémis à Ephèse",wonderBoards.get(2).getWonderName());
        assertEquals("Les jardins suspendus de Babylone",wonderBoards.get(3).getWonderName());
        assertEquals("La statue de Zeus à Olympie",wonderBoards.get(4).getWonderName());
        assertEquals("Le mausolée d’Halicarnasse",wonderBoards.get(5).getWonderName());
        assertEquals("La grande pyramide de Gizeh",wonderBoards.get(6).getWonderName());

        assertEquals( MaterialType.ORES,wonderBoards.get(0).getMaterialEffect().getMaterials()[0].getType());
        assertEquals( MaterialType.GLASS,wonderBoards.get(1).getMaterialEffect().getMaterials()[0].getType());
        assertEquals(MaterialType.PAPYRUS,wonderBoards.get(2).getMaterialEffect().getMaterials()[0].getType());
        assertEquals(MaterialType.CLAY,wonderBoards.get(3).getMaterialEffect().getMaterials()[0].getType());
        assertEquals(MaterialType.WOOD,wonderBoards.get(4).getMaterialEffect().getMaterials()[0].getType());
        assertEquals(MaterialType.FABRIC,wonderBoards.get(5).getMaterialEffect().getMaterials()[0].getType());
        assertEquals(MaterialType.STONE,wonderBoards.get(6).getMaterialEffect().getMaterials()[0].getType());

        assertEquals(7,wonderBoards.size());
    }

    @Test
    void chooseWonderBoardTest() {
        ArrayList<WonderBoard> wonderBoardsBase = wonderBoardFactory.createWonderBoard();

        for(int j=0;j<100;j++) {
            for (int i = 0; i < 7; i++) {
                ArrayList<WonderBoard> wonderBoards = wonderBoardFactory.chooseWonderBoard(i);
                assertTrue(i == wonderBoards.size());//La taille correspond au nombre de joueur.

                for (WonderBoard wb : wonderBoards) {
                    wonderBoardsBase.contains(wb);//Elle existe dans les Merveilles
                }
            }
        }
    }
}
