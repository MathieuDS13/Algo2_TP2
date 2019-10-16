public class Main {

    public static void main(String[] args){

        long start = System.nanoTime();
        String filepath = "C:\\Users\\Utilisateur\\Documents\\Cours L3 informatique\\Algorithmique 2\\TP\\TP2\\dico.txt";

        Dico dico = DicoParser.parseFileToDico(filepath);
        System.out.println(dico.contains("banane"));

        System.out.println(String.format("Temps écoulé : %.2f ms", (System.nanoTime() - start)/1e6));
    }
}
