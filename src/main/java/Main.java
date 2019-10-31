public class Main {

    public static void main(String[] args){

        long start = System.nanoTime();
        String dicoPath = "C:\\Users\\Utilisateur\\Documents\\Cours L3 informatique\\Algorithmique 2\\TP\\TP2\\dico.txt";
        String filePath = "C:\\Users\\Utilisateur\\Documents\\Cours L3 informatique\\Algorithmique 2\\TP\\TP2\\fautes.txt";
        FileCorrector corrector = new FileCorrector(dicoPath, filePath);
        corrector.printCorrections();
        System.out.println(String.format("Temps écoulé : %.2f ms", (System.nanoTime() - start)/1e6));
    }
}
