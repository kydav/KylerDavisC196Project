package wgu.com.listview;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app populates a ListView from an array with each item in the list being
 * a button. Clicking on a button results in a toast. If we had more courses in
 * the array, the ListView would automatically add a scroll bar.
 *
 * You will see that an ArrayAdapter is used to construct the ListAdapter.
 * So, the ListView is said to be driven by a data adapter.
 */


// Changed from default "extends AppCompatActivity" to extends ListActivity
public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the "courses" string array
        String[] courses = new String[] {"C169", "C188", "C196",
                "C482", "EDV1", "TXC1", "TXP1", "TYC1", "TYP1"};

        // Create the ArrayAdapter using our "courses" string array.
        // The "android.R.layout.simple_list_item_1" defines the button layout.
        ListAdapter courseAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, courses);

        // Pass our adapter to the ListView (we extended the ListActivity class)
        setListAdapter(courseAdapter);

        // Here we are simply creating a setOnItemClickListener so we can generate
        // a toast when a button is clicked.
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = ((TextView) view).getText() + " is in array position " + position;
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
        });
    }
}
