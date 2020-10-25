package commun.request;

import commun.action.DiscardAction;
import commun.card.Card;
import commun.card.Deck;
import commun.effect.EffectList;
import commun.effect.ScientificType;
import commun.wonderboard.WonderBoard;
import log.GameLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestPlayerActionCheckTest {
    RequestPlayerActionCheck check;
    Deck deck;
    RequestToPlayer ia;

    @BeforeEach
    void init(){
        GameLogger.verbose =false;
        ia = Mockito.mock(RequestToPlayer.class);
        check = new RequestPlayerActionCheck(ia);
    }

    @Test
    void chooseAction() {

        Mockito.when(ia.chooseAction(Mockito.any(Deck.class),Mockito.anyInt(),Mockito.any(EffectList.class)))
                .thenReturn(new DiscardAction(-1),new DiscardAction(5),null, new DiscardAction(1));
        deck = new Deck();
        for(int i = 0; i<5;i++) deck.addCard(new Card(null,null,null,0,null)); //deck de 5 carte

        //on a un deck de 5 carte
        check.chooseAction(deck,0,new EffectList());
        //on a bien appeler la methode chooseAction de l'ia jusqua ce quelle nous donne un retour valide (4 fois -1,5,null,1)
        Mockito.verify(ia, Mockito.times(4)).chooseAction(Mockito.any(Deck.class),Mockito.anyInt(),Mockito.any(EffectList.class));
    }

    @Test
    void choosePurchasePossibility() {
        Integer[] ints;
        List<Integer[]> list = new ArrayList<>();
        for(int i = 0; i<10; i++){
            ints = new Integer[2];
            ints[0] = i;
            ints[1] = i/2;
            list.add(ints);
        }
        //les 3 premiere sont pas dans la liste et la derniere ne devrait pas etre appeller car l'avant deniere est correcte
        Mockito.when(ia.choosePurchasePossibility(Mockito.any()))
                .thenReturn(new Integer[]{1,2,3},new Integer[]{2,4},new Integer[]{},new Integer[]{8,4}, new Integer[]{8,4});

        check.choosePurchasePossibility(list); //on a bien 4 demande car les 3 premiere sont mauvaise
        Mockito.verify(ia,Mockito.times(4)).choosePurchasePossibility(list);

    }

    @Test
    void useScientificsGuildEffect() {
        Mockito.when(ia.useScientificsGuildEffect(Mockito.any()))
                .thenReturn(null,null, ScientificType.GEOMETRY);

        check.useScientificsGuildEffect(new WonderBoard(null,null)); //on a bien 3 demande car les 2 premiere sont mauvaise
        Mockito.verify(ia,Mockito.times(3)).useScientificsGuildEffect(Mockito.any());
    }

    @Test
    void chooseCard() {
        Mockito.when(ia.chooseCard(Mockito.any(Deck.class)))
                .thenReturn(-1,5,-2, 1);

        deck = new Deck();
        for(int i = 0; i<5;i++) deck.addCard(new Card(null,null,null,0,null)); //deck de 5 carte

        //on a un deck de 4 carte
        check.chooseCard(deck);
        //on a bien appeler la methode chooseCard de l'ia jusqua ce quelle nous donne un retour valide (4 fois -1,5,-2,1)
        Mockito.verify(ia, Mockito.times(4)).chooseCard(Mockito.any(Deck.class));

    }

    @Test
    void testToString ()
    {
        Mockito.when(check.toString()).thenReturn("RandomIA");
        assertEquals(check.toString(), "RandomIA");
    }
}