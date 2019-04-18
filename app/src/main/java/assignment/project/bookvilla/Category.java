package assignment.project.bookvilla;

import java.util.HashMap;

// A HashMap to map each category to corresponding keyword required to build request url.

public class Category {

    private HashMap<String,String> map;

    Category(){

        map = new HashMap<>();
        map.put("Author","inauthor"); //inauthor: to return results where the text following this keyword is found in the author.
        map.put("Book Name","intitle"); //intitle: to return results where the text following this keyword is found in the title.
        map.put("ISBN Number","isbn"); //isbn: to retrieve information for a specific volume
    }

    public String getCategory(String category){

        return map.get(category);
    }
}
