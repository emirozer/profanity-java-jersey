package io.ghost.emirozer;

import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.logging.Logger;


/**
 * Root resource (exposed at "filter" path)
 */
@Path("filter")
public class Filter {
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */

    @GET
    @Consumes("text/plain")
    @Produces(MediaType.TEXT_PLAIN)
    public String filterIt(@QueryParam("input") String toBeFiltered,
                           @QueryParam("lang") String lang) {

        final Logger logger = Logger.getLogger(Filter.class.getName());

        String [] langArray = new String[22];
        langArray = new String[]{"cs", "da", "de", "en", "eo", "es", "fi", "fr", "hu", "it",
                "ja", "ko", "nl", "no", "pl", "pt", "ru", "sv", "th", "tlh", "tr", "zh"};

        String final_result = "";
        String filtered = "";
        String[] list_splitted = toBeFiltered.split(" ");

        if (toBeFiltered.equals("")){

            return "Empty string cannot be filtered!";

        }else {

            logger.warning("Received query param : input ---> " + toBeFiltered);

        }

        if (lang.equals("") || lang.isEmpty()){

            logger.warning("Did not received query param : lang ---> defaults to \"en\" ");

            for (int i=0; i < list_splitted.length; i++) {
                filtered = filteringProcessor("", list_splitted[i]);

                list_splitted[i] = filtered;
            }
        }else {
            logger.warning("Received query param : lang ---> " + lang);

            for(String lang_code : langArray){
                if (lang_code.equals(lang)) {
                for (int i=0; i < list_splitted.length; i++){
                    // Check if the lang query param matches any lang_code in langArray

                        // check for that specific file
                        filtered = filteringProcessor(lang, list_splitted[i]);

                        list_splitted[i] = filtered;

                    }
                }
            }

        }


        for(String sp : list_splitted){
            final_result += sp + " ";
        }
        final_result = final_result.substring(0, final_result.length() - 1);
        logger.warning(final_result);
        return final_result;
    }

    public String filteringProcessor(String langToBeFound, String inputToBeFiltered){

        final Logger logger = Logger.getLogger(Filter.class.getName());

        String folderPath = "src/List-of-Dirty-Naughty-Obscene-and-Otherwise-Bad-Words/";

        java.nio.file.Path path = Paths.get(folderPath, langToBeFound);
        Charset charset = Charset.forName("ISO-8859-1");
        String line;
        String temp = "";
        String leet_temp = "";

        if (langToBeFound.equals("")){

            File dir = new File(folderPath);
            File[] directoryListing = dir.listFiles();

            if (directoryListing != null) {

                for (File child : directoryListing) {

                    // Do something with child
                    String path_to_child = child.getAbsolutePath();
                    java.nio.file.Path path_to_specific = Paths.get(path_to_child);

                    try (BufferedReader reader = Files.newBufferedReader(path_to_specific, charset)) {
                        while ((line = reader.readLine()) != null ) {

                            if(line.equals(inputToBeFiltered)){
                                for(int x=0; x< inputToBeFiltered.length();x++){
                                        temp += "*";
                                     }
                            return temp;
                            } else{
                                leet_temp = leetReplace(line);

                                if(leet_temp.equals(inputToBeFiltered)){

                                    logger.warning("Original input matched with l33t speak --> " + leet_temp);
                                    
                                    for(int x=0; x< inputToBeFiltered.length();x++){
                                        temp += "*";
                                    }
                                    return temp;
                                }
                            }
                        }
                    } catch (IOException e) {
                        logger.severe(String.valueOf(e));
                    }
                }

            } else {
                logger.severe("Curse word dictionary files cannot be found! You forgot to init git submodule");
            }

        }else{
            try (BufferedReader reader = Files.newBufferedReader(path, charset)) {

                while ((line = reader.readLine()) != null ) {

                    if(line.equals(inputToBeFiltered)){

                        for(int x=0; x< inputToBeFiltered.length();x++){
                            temp += "*";

                        }
                        return temp;
                    } else{
                        leet_temp = leetReplace(line);

                        if(leet_temp.equals(inputToBeFiltered)){

                            logger.warning("Original input matched with l33t speak --> " + leet_temp);

                            for(int x=0; x< inputToBeFiltered.length();x++){
                                temp += "*";
                            }
                            return temp;
                        }
                    }
                }
            } catch (IOException e) {
                logger.severe(String.valueOf(e));
            }
        }

        return inputToBeFiltered;
    }
    public String leetReplace(String inputToBeReplaced){

        if (inputToBeReplaced.contains("a")){
            inputToBeReplaced = inputToBeReplaced.replace("a", "4");
        }
        if (inputToBeReplaced.contains("e")){
            inputToBeReplaced = inputToBeReplaced.replace("e", "3");
        }
        if (inputToBeReplaced.contains("g")){
            inputToBeReplaced = inputToBeReplaced.replace("g", "9");
        }
        if (inputToBeReplaced.contains("o")){
            inputToBeReplaced = inputToBeReplaced.replace("o", "0");
        }
        if (inputToBeReplaced.contains("y")){
            inputToBeReplaced = inputToBeReplaced.replace("y", "¥");
        }
        if (inputToBeReplaced.contains("i")){
            inputToBeReplaced = inputToBeReplaced.replace("i", "1");
        }

        return inputToBeReplaced;

    }

}
