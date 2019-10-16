import java.io.*;
import java.nio.file.Files;
import java.util.Set;
import java.util.stream.Collectors;

public class DicoParser {

    public static Dico parseFileToDico(String filepath){

        Dico dico = new Dico();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;

            while((line = reader.readLine()) != null){
                dico.addWord(line.toLowerCase());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return dico;
    }

}
