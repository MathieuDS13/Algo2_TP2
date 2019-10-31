import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileCorrector {

  Dico dico;
  OrthographicCorrector corrector;
  String filepath;

    public FileCorrector(String dicoPath, String filepath) {
        this.dico = DicoParser.parseFileToDico(dicoPath);
        this.corrector = new OrthographicCorrector(dico);
        this.filepath = filepath;
    }

    public void printCorrections(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;

            while((line = reader.readLine()) != null){
                corrector.correctWord(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
