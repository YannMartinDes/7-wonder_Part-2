package commun.utils;

import java.security.SecureRandom;
import java.util.Random;

public class SingletonRandom
{
    private final static Random random = new SecureRandom();

    public static Random getInstance ()
    { return random; }
}
