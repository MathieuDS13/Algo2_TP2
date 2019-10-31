import java.lang.reflect.Array;
import java.util.*;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.toMap;

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

    private static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    public static int calculateWordDistance(String leftWord, String rightWord) {
        int[][] costMatrix = new int[leftWord.length() + 1][rightWord.length() + 1];
        for (int indexFirstWord = 0; indexFirstWord <= leftWord.length(); indexFirstWord++) {
            for (int indexSecondWord = 0; indexSecondWord <= rightWord.length(); indexSecondWord++) {
                if (indexFirstWord == 0) {
                    costMatrix[indexFirstWord][indexSecondWord] = indexSecondWord;
                } else if (indexSecondWord == 0) {
                    costMatrix[indexFirstWord][indexSecondWord] = indexFirstWord;
                } else {
                    costMatrix[indexFirstWord][indexSecondWord] = min(costMatrix[indexFirstWord - 1][indexSecondWord - 1]
                                    + costOfSubstitution(leftWord.charAt(indexFirstWord - 1), rightWord.charAt(indexSecondWord - 1)),
                            costMatrix[indexFirstWord - 1][indexSecondWord] + 1,
                            costMatrix[indexFirstWord][indexSecondWord - 1] + 1);
                }
            }
        }
        return costMatrix[leftWord.length()][rightWord.length()];
    }

    private static int costOfSubstitution(char firstChar, char secondChar) {
        return firstChar == secondChar ? 0 : 1;
    }

    public void printWordSugestions(String origin, /*tableau de mots*/ArrayList<String> suggestions) {
        System.out.print(origin + " -> ");
        for (String suggestion :
                suggestions) {
            System.out.print(suggestion + " ; ");
        }
        System.out.print("\n");
    }

    private ArrayList<String> processPossibleWords(String from) {
        ArrayList<String> possibilities = new ArrayList<>();
        if (dico.contains(from)) {
            possibilities.add(from);
            return possibilities;
        }
        return new ArrayList<>();


        //Calculer les trigrammes de from
        //Extraire les mots qui ont au moins un trigramme en commun
        //Calculer le nombre d'occurence de chaque mot dans la liste des mots associés aux trigrammes de from
        //-> ceci donne un poids, renvoyer les 100 mots de plus haut poids puis lancer calculateAndPrintSuggestions()


        //Calculer les 100 mots qui ont le plus de trigrammes communs avec le mot from et les renvoyer
    }


    public void calculateAndPrintSuggestions(String word, ArrayList<String> possibilities) {
        if (possibilities.size() == 1) {
            printWordSugestions(word, possibilities);
        }
        ArrayList<String> suggestions = new ArrayList<>();
        printWordSugestions(word, suggestions);
    }


    private static ArrayList<String> computeTrigrammes(String word) {
        ArrayList<String> trigrammes = new ArrayList<>();
        for (int index = 0; index < word.length() - 3; index++) {
            String trigramme = word.substring(index, index + 3);
            if (trigramme.length() <= 2) continue;
            trigrammes.add(trigramme);
        }
        return trigrammes;
    }

    public void correctWord(String word) {
        ArrayList<String> wordTrigrammes = computeTrigrammes(word);
        Map<String, Integer> possibilities = new HashMap<>();

        for (String trigramme :
                wordTrigrammes) {
            for (String wordPossibility :
                    this.trigrammes.getOrDefault(trigramme, new HashSet<String>() {
                    })) {
                if(wordPossibility.isEmpty()) continue;
                possibilities.put(wordPossibility, possibilities.getOrDefault(wordPossibility, 0) + 1);
            }
        }

        LinkedHashMap sorted = possibilities
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        String[] possibilitiesArray = (String[]) sorted.keySet().toArray(new String[100]);

        int[] distances = new int[5];
        ArrayList<String> bestPossibilities = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            if(possibilitiesArray[index] == null) break;
            int distance = calculateWordDistance(possibilitiesArray[index], word);
            if (bestPossibilities.size() < 5) {
                distances[bestPossibilities.size()] = distance;
                bestPossibilities.add(possibilitiesArray[index]);
            } else {
                for (int distanceIndex = 0; distanceIndex < 5; distanceIndex++) {
                    if (distances[distanceIndex] > distance) {
                        distances[distanceIndex] = distance;
                        bestPossibilities.set(distanceIndex, possibilitiesArray[index]);
                        break;
                    }
                }
            }
        }

        printWordSugestions(word, bestPossibilities);

    }
}


//TODO Implémenter le calcul de distance entre deux mots du dictionnaire

//TODO Implémenter la construction des trigrammes d'un mot

//TODO calculer le nombre d'occurences d'un mot dans la liste des mots associés aux trigrammes du mot

//TODO sélectionner 100 mots qui ont le le plus de trigrammes communs avec le mot

//TODO Choisir parmis 5 mots possibles le bon selon la distance d'édition

