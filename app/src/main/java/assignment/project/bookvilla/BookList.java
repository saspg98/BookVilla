package assignment.project.bookvilla;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import book_model.Book;

//Activity that is used to display the view of all the books prepared using ArrayAdapter

public class BookList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Book> books = (ArrayList<Book>) args.getSerializable("LIST");

        //Invoking ArrayAdapter using BookAdapter class which implements ArrayAdapter
        BookAdapter bookAdapter = new BookAdapter(this, getApplicationContext(), books);

        ListView listView = (ListView) findViewById(R.id.list_view_movie);
        listView.setAdapter(bookAdapter);                                       //add the book views to this activity

    }
}
