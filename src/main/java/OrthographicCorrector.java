import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrthographicCorrector {

    private Dico dico;

    HashMap<String, Set<String>> trigrammes;

    public OrthographicCorrector(Dico dico) {
        this.dico = dico;
        this.trigrammes = new HashMap<>();

        for (String word :
                dico.getWords()) {
            computeAndAddTrigrammes("<" + word + ">");
        }
    }

    public void computeAndAddTrigrammes(String word) {

        for (int index = 0; index < word.length() - 3; index++) {
            String trigramme = word.substring(index, index + 3);
            if (trigramme.length() <= 2) continue;
            trigrammes.computeIfAbsent(trigramme, k -> new HashSet<>());
            trigrammes.get(trigramme).add(word);
        }
    }
}


//TODO Implémenter le calcul de distance entre deux mots du dictionnaire

//TODO Implémenter la construction des trigrammes d'un mot

//TODO calculer le nombre d'occurences d'un mot dans la liste des mots associés aux trigrammes du mot

//TODO sélectionner 100 mots qui ont le le plus de trigrammes communs avec le mot

//TODO Choisir parmis 5 mots possibles le bon selon la distance d'édition

