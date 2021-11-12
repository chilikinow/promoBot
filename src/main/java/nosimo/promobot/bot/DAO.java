package nosimo.promobot.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DAO <T,S>{
    List<T> get(S s);
    List<T> getAll(S s);
}

