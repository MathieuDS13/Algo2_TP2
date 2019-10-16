import java.util.HashSet;
import java.util.Set;

public class Dico {

    private Set<String> words;

    public Dico() {
        this.words = new HashSet<>();
    }

    public void addWord(String word){
        words.add(word);
    }

    public boolean contains(String word){
        return words.contains(word);
    }

}
