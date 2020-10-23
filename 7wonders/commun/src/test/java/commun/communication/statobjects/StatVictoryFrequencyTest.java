package commun.communication.statobjects;

import commun.communication.StatObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatVictoryFrequencyTest
{
    private StatVictoryFrequency victoryFrequency;

    @BeforeEach
    public void init ()
    {
        this.victoryFrequency = new StatVictoryFrequency(2);
    }

    private ArrayList<Integer> arrayListTest ()
    {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        return arrayList;
    }

    private ArrayList<String> statUsernames ()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("/");
        arrayList.add("A");
        arrayList.add("B");
        return arrayList;
    }

    private ArrayList<String> gameUsernames ()
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        return arrayList;
    }

    @Test
    public void testAdd ()
    {
        StatObject statObject = new StatObject();
        statObject.construct(2);
        statObject.setUsernames(this.statUsernames());

        // Ajouter un vainqueur
        statObject.getVictoryFrequency().setStat(this.arrayListTest());

        // Test taille
        assertEquals(statObject.getVictoryFrequency().getStat().size(), this.victoryFrequency.getStat().size());
        // Test pre-traitement
        for (int i = 0; i < this.victoryFrequency.getStat().size(); i++)
        {
            assertEquals(this.victoryFrequency.getStat().get(i), 0);
        }

        // "A" gagne
        this.victoryFrequency.add(statObject, this.gameUsernames());

        // Test post-traitement
        assertEquals(this.victoryFrequency.getStat().size(), 2);
        assertEquals(this.victoryFrequency.getStat().get(0), 1);
        assertEquals(this.victoryFrequency.getStat().get(1), 0);

        // "A" gagne encore
        this.victoryFrequency.add(statObject, this.gameUsernames());

        // Test post-traitement
        assertEquals(this.victoryFrequency.getStat().size(), 2);
        assertEquals(this.victoryFrequency.getStat().get(0), 2);
        assertEquals(this.victoryFrequency.getStat().get(1), 0);
    }
}
