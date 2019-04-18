package assignment.project.bookvilla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import book_model.Book;
import book_model.BookResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xdroid.toaster.Toaster;

public class MainActivity extends AppCompatActivity implements InternetConnectionListener {

    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private static String search = "";
    private static String category = "";
    private Spinner spinner;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private SharedPreferences settings;
    private Set<String> history;

    //First method called when app starts
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding dropdown to the screen/view

        spinner = (Spinner) findViewById(R.id.spinner);
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.choice, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = new Category().getCategory(parent.getSelectedItem().toString());
                Log.e("Selection", "Item selected is:" + category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                category = "intitle";
            }
        });

        //Adding editable text field which shows previous queries
        settings = getSharedPreferences(PREFS_NAME, 0);
        history = settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());
        setAutoCompleteSource();

        Cache_Network.setInternetConnectionListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Cache_Network.removeInternetConnectionListener();
    }

    @Override
    protected void onStop() {
        super.onStop();

        savePrefs();
    }

    // Calls the Cache_Network class to provide retrofit object required to make request
    //Process the response received from the API
    //Builds the next screen
    private void getGBookService() {

        GBookClient gBookClient = Cache_Network.getGBookClient();

        //Checking what is the request according GBook Client are invoked

        if (category != "isbn") {

            Call<BookResponse> call = gBookClient.getBooks(search, "full", 40);

            Log.w("URL", call.request().url().toString());

            final double reqTime = System.currentTimeMillis();
            call.enqueue(new Callback<BookResponse>() {

                @Override
                public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {

                    double mElapsedTime = System.currentTimeMillis() - reqTime;

                    String source = "";

                    if (response.raw().cacheResponse() != null) {
                        source = "Cache";                           //Notifies whether data is from cache or not
                        Log.e("Data From", "Cache");
                    }

                    if (response.raw().networkResponse() != null) {
                        source = "API";
                        Log.e("Data From", "API");          //Notifies whether data is from cache or not
                    }


                    //Displays the elapsed time between request and response in case cache is used or API call
                    Toast.makeText(getBaseContext(), "Data from: " + source + "  Time(ms): " +
                            String.valueOf(mElapsedTime), Toast.LENGTH_LONG).show();

                    if (!response.isSuccessful()) {
                        Log.e("Failure", "Null value from API call");
                    } else {
                        ArrayList<Book> books = (ArrayList<Book>) response.body().getItems();
                        try {

                            Log.e("Success", "Number of movies received: " + books.size());

                            //Request to build the next screen containing the books received as response
                            createNextActivity(books);

                        } catch (Exception e) {

                            //If no books matched the query on Google Books
                            Toast.makeText(getBaseContext(), "No such book exists!"
                                    , Toast.LENGTH_LONG).show();
                        }

                    /*double elapsedTime= - response.raw().sentRequestAtMillis() +
                            response.raw().receivedResponseAtMillis();*/

                        // Log.e("Send request At:",String.valueOf(response.raw().sentRequestAtMillis()));
                        // Log.e("Elapsed time",String.valueOf(elapsedTime));
                        //  Log.e("Monitored elapsed time",String.valueOf(mElapsedTime));
                    }
                }

                @Override
                public void onFailure(Call<BookResponse> call, Throwable t) {
                    Log.e("Failure", t.toString());
                    Log.e("Failure", "Milgai error");
                    Toast.makeText(getBaseContext(), "Cannot connect to API", Toast.LENGTH_LONG).show();
                }
            });
        } else {    //if search is done using ISBN Number

            Call<Book> call = gBookClient.getIsbnBook(search);

            Log.w("URL", call.request().url().toString());

            final double reqTime = System.currentTimeMillis();
            call.enqueue(new Callback<Book>() {

                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {

                    double mElapsedTime = System.currentTimeMillis() - reqTime;

                    String source = "";

                    if (response.raw().cacheResponse() != null) {
                        source = "Cache";
                        Log.e("Data From", "Cache");
                    }

                    if (response.raw().networkResponse() != null) {
                        source = "API";
                        Log.e("Data From", "API");
                    }

                    Toast.makeText(getBaseContext(), "Data from: " + source + "  Time(ms): " +
                            String.valueOf(mElapsedTime), Toast.LENGTH_LONG).show();

                    if (!response.isSuccessful()) {
                        Log.e("Failure", "Null value from API call");
                    } else {
                        Book book = (Book) response.body();
                        try {

                            Log.e("Success", "Book recieved: " + book.getVolumeInfo().getTitle());
                            ArrayList<Book> books = new ArrayList<>();
                            books.add(book);
                            createNextActivity(books);

                        } catch (Exception e) {

                            Toast.makeText(getBaseContext(), "No such book exists!"
                                    , Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Log.e("Failure", t.toString());
                    Toast.makeText(getBaseContext(), "Cannot connect to API", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //Method to create next screen and display the books

    private void createNextActivity(ArrayList<Book> books) {

        Intent nextActivity = new Intent(this, BookList.class);
        Bundle args = new Bundle();
        args.putSerializable("LIST", books);
        nextActivity.putExtra("BUNDLE", args);
        startActivity(nextActivity);
    }

    //Method called when CONTINUE button on screen is clicked

    public void getBook(View view) {

        TextView searchBook = (TextView) findViewById(R.id.search);
        search = searchBook.getText().toString().trim();

        if (category != "isbn")
            search = search + "+" + category;

        //adding searched item to text field history

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.search);
        addSearchInput(textView.getText().toString());

        getGBookService();
    }

    private void setAutoCompleteSource() {
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, history.toArray(new String[history.size()]));
        textView.setAdapter(adapter);
    }

    private void addSearchInput(String input) {
        if (!history.contains(input)) {
            history.add(input);
            setAutoCompleteSource();
        }
    }

    private void savePrefs() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, history);

        editor.commit();
    }

    @Override
    public void onInternetUnavailable() {
        Toaster.toast("No Internet Available!", Toast.LENGTH_LONG);
        Log.e("OnInternetUnavailable", "No internet!");
    }

    @Override
    public void onCacheUnavailable() {
        Toaster.toast("No Cache Available!", Toast.LENGTH_LONG);
        Log.e("OnCacheUnavailable", "No stored cache!");
    }
}
