package assignment.project.bookvilla;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import book_model.Book;

//Designs layout for each book item and display together on screen to make recycle view
class BookAdapter extends ArrayAdapter<Book> {

    private static Context app;
    private static Context listView;

    BookAdapter(Context current, Context app, ArrayList<Book> books) {

        super(current, 0, books);
        listView = current;
        this.app = app;
    }

    //Called by ListView(BookList class) to make View for each book(listItemView) received in response
    //and attach the View to the ListView using ArrayAdapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book book = getItem(position);    //get book from arrayAdapter

        View listItemView = convertView;

        //Preparing View for each book

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }

        //Adding book's image,title,author etc to the View prepared

        ImageView bookImage = (ImageView) listItemView.findViewById(R.id.book_image);
        String smBookImg = book.getVolumeInfo().getImageLinks().getSmallThumbnail();
        String bgBookImg = book.getVolumeInfo().getImageLinks().getThumbnail();
        if (bgBookImg != null)
            setMovieImage(app, bookImage, bgBookImg);
        else if (smBookImg != null)
            setMovieImage(app, bookImage, smBookImg);

        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        bookTitle.setText(book.getVolumeInfo().getTitle());

        TextView date = (TextView) listItemView.findViewById(R.id.publish_date);
        if (book.getVolumeInfo().getPublishedDate() != null)
            date.setText(book.getVolumeInfo().getPublishedDate());
        else
            date.setVisibility(View.GONE);

        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        if (!book.getVolumeInfo().getAuthors().isEmpty()) {

            String authors = String.join(", ", book.getVolumeInfo().getAuthors());
            bookAuthor.setText(authors);
        } else
            bookAuthor.setVisibility(View.GONE);

        TextView isbn = (TextView) listItemView.findViewById(R.id.isbn);
        isbn.setText(book.getId());

        final TextView webLink = (TextView) listItemView.findViewById(R.id.web_link);
        LinearLayout web = (LinearLayout) listItemView.findViewById(R.id.web);
        if (book.getAccessInfo() != null) {

            webLink.setText(book.getAccessInfo().getWebReaderLink());
            webLink.setTextColor(getContext().getResources().getColor(R.color.blue));

            // to allow web search by clicking on the link provided for each google book

            webLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Create the text message with a string
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_WEB_SEARCH);
                    sendIntent.putExtra(SearchManager.QUERY, book.getAccessInfo().getWebReaderLink());

                    // Verify that the intent will resolve to an activity
                    if (sendIntent.resolveActivity(listView.getPackageManager()) != null) {
                        listView.startActivity(sendIntent);
                    }
                }
            });
        } else
            web.setVisibility(View.GONE);

        return listItemView;
    }

    private void setMovieImage(Context context, ImageView bookImage, String image_url) {

        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(bookImage);
    }
}
