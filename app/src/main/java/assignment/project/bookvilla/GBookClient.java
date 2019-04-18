package assignment.project.bookvilla;

import book_model.Book;
import book_model.BookResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//Interface that will be used to make request/response to Google Book API.

public interface GBookClient {

    //Use to make request to Google Book API if searched is made using Book Name or Author.
    @GET("volumes")
    Call<BookResponse> getBooks(@Query("q") String search,@Query("projection") String projection
            ,@Query("maxResults") int maxResults);

    //Use to make request to Google Book API if searched is made using ISBN Number.
    @GET("volumes/{isbn}")
    Call<Book> getIsbnBook(@Path("isbn") String isbn);
}
