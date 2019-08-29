package com.wgu.kylerdavisc196project;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.wgu.kylerdavisc196project.model.Assessment;
import com.wgu.kylerdavisc196project.model.Course;
import com.wgu.kylerdavisc196project.model.Term;

public class MainActivity extends AppCompatActivity {
    private QueryManager QM;
    private CoordinatorLayout coordinatorLayout;
    private long termId;
    private ImageButton currentTermButton;
    private ImageButton createNewTermButton;
    private ImageButton termListButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Term Scheduler");
        QM = new QueryManager(MainActivity.this);
        hideShowTermList();
        hideShowCurrentTerm();
        currentTermButton = findViewById(R.id.currentTermButton);
        currentTermButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(),TermView.class);
            QM.open();
            termId = QM.currentTerm().getId();
            QM.close();
            intent.putExtra(TermDbHandler.TERM_ID, termId);
            startActivity(intent);}
        });
        createNewTermButton = findViewById(R.id.createTermButton);
        createNewTermButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TermEdit.class);
                startActivity(intent);
            }
        });
        termListButton = findViewById(R.id.termListButton);
        termListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TermList.class);
                startActivity(intent);
            }
        });
        QM.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_generate_test_data) {
            try {
                generateTestData();
            }catch(Exception e) {
                System.out.println(e);
            }
            hideShowTermList();
            hideShowCurrentTerm();
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Test Data Created", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return true;
        }
        if (id == R.id.action_delete_all_data) {
            try {
                QM.open();
                QM.deleteData();
                QM.close();
            }catch(Exception e) {
                System.out.println(e);
            }
            hideShowTermList();
            hideShowCurrentTerm();
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "All Data Deleted", Snackbar.LENGTH_SHORT);
            snackbar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        hideShowTermList();
        hideShowCurrentTerm();
    }


    public void hideShowTermList() {
        QM.open();
        if(!QM.isTermEmpty()) {
            ImageButton termListButton = findViewById(R.id.termListButton);
            termListButton.setVisibility(View.VISIBLE);
        } else {
            ImageButton termListButton = findViewById(R.id.termListButton);
            termListButton.setVisibility(View.INVISIBLE);
        }
        QM.close();
    }
    public void hideShowCurrentTerm() {
        QM.open();
        if(QM.currentTermExists()) {
            ImageButton currentTermButton = findViewById(R.id.currentTermButton);
            currentTermButton.setVisibility(View.VISIBLE);
        }else {
            ImageButton currentTermButton = findViewById(R.id.currentTermButton);
            currentTermButton.setVisibility(View.INVISIBLE);
        }
        QM.close();
    }

    public void generateTestData() {
        QM.open();
        Term termOne = new Term(1, "Term 1", "20190701", "20191231");
        QM.insertTerm(termOne);
        Term termTwo = new Term(2, "Term 2", "20200101", "20200630");
        QM.insertTerm(termTwo);
        Term termThree = new Term(3, "Term 3", "20200701", "20201231");
        QM.insertTerm(termThree);
        Resources res = getResources();
        String[] statusStrings = res.getStringArray(R.array.status_array);

        Course courseOne = new Course(1, "Course 1", "Course 1 Description", "20190701", "20190831", "James Smith", "123-456-1234", "james.smith@wgu.edu", statusStrings[1], termOne);
        QM.insertCourse(courseOne);
        Course courseTwo = new Course(2, "Course 2", "Course 2 Description", "20190901", "20191031", "Michael Smith", "123-456-1235", "michael.smith@wgu.edu", statusStrings[0], termOne);
        QM.insertCourse(courseTwo);
        Course courseThree = new Course(3, "Course 3", "Course 3 Description", "20191101", "20201231", "Robert Smith", "123-456-1236", "robert.smith@wgu.edu", statusStrings[0], termOne);
        QM.insertCourse(courseThree);
        Course courseFour = new Course(4, "Course 4", "Course 4 Description", "20200101", "20200229", "Maria Garcia", "123-456-1237", "maria.garcia@wgu.edu", statusStrings[0], termTwo);
        QM.insertCourse(courseFour);
        Course courseFive = new Course(5, "Course 5", "Course 5 Description", "20200301", "20200430", "David Smith", "123-456-1238", "david.smith@wgu.edu", statusStrings[0], termTwo);
        QM.insertCourse(courseFive);
        Course courseSix = new Course(6, "Course 6", "Course 6 Description", "20200501", "20200630", "Maria Rodriguez", "123-456-1239", "maria.rodriquez@wgu.edu", statusStrings[0], termTwo);
        QM.insertCourse(courseSix);
        Course courseSeven = new Course(7, "Course 7", "Course 7 Description", "20200701", "20200831", "Mary Smith", "123-456-1241", "mary.smith@wgu.edu", statusStrings[0], termThree);
        QM.insertCourse(courseSeven);
        Course courseEight = new Course(8, "Course 8", "Course 8 Description", "20200901", "20201031", "Maria Hernandez", "123-456-1242", "maria.hernandez@wgu.edu", statusStrings[0], termThree);
        QM.insertCourse(courseEight);
        Course courseNine = new Course(9, "Course 9", "Course 9 Description", "20201101", "20201231", "James Johnson", "123-456-1243", "james.johnson@wgu.edu", statusStrings[0], termThree);
        QM.insertCourse(courseNine);
        String[] assessmentTypeStrings = res.getStringArray(R.array.assessment_type_array);
        Assessment assessmentOne = new Assessment(1, "Course 1 Assessment", "20190831", assessmentTypeStrings[0], 1);
        QM.insertAssessment(assessmentOne);
        Assessment assessmentTwo = new Assessment(2, "Course 2 Assessment", "20191031", assessmentTypeStrings[1], 2);
        QM.insertAssessment(assessmentTwo);
        Assessment assessmentThree = new Assessment(3, "Course 3 Assessment", "20191231", assessmentTypeStrings[0], 3);
        QM.insertAssessment(assessmentThree);
        Assessment assessmentFour = new Assessment(4, "Course 4 Assessment", "20200229", assessmentTypeStrings[1], 4);
        QM.insertAssessment(assessmentFour);
        Assessment assessmentFive = new Assessment(5, "Course 5 Assessment", "20200430", assessmentTypeStrings[0], 5);
        QM.insertAssessment(assessmentFive);
        Assessment assessmentSix = new Assessment(6, "Course 6 Assessment", "20200630", assessmentTypeStrings[0], 6);
        QM.insertAssessment(assessmentSix);
        Assessment assessmentSeven = new Assessment(7, "Course 7 Assessment", "20200831", assessmentTypeStrings[1], 7);
        QM.insertAssessment(assessmentSeven);
        Assessment assessmentEight = new Assessment(8, "Course 8 Assessment", "20201031", assessmentTypeStrings[1], 8);
        QM.insertAssessment(assessmentEight);
        Assessment assessmentNine = new Assessment(9, "Course 9 Assessment", "20201231", assessmentTypeStrings[1], 9);
        QM.insertAssessment(assessmentNine);

        QM.insertNote("Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Sem et tortor consequat id porta nibh venenatis cras sed. " +
                "Consectetur lorem donec massa sapien faucibus. " +
                "Viverra orci sagittis eu volutpat odio facilisis mauris sit amet. " +
                "A erat nam at lectus urna. Diam quis enim lobortis scelerisque. " +
                "Sit amet nisl purus in mollis nunc sed id semper. Neque vitae tempus quam pellentesque nec. " +
                "Vulputate mi sit amet mauris. Mi in nulla posuere sollicitudin aliquam ultrices sagittis orci a. " +
                "At ultrices mi tempus imperdiet. Facilisi etiam dignissim diam quis enim lobortis scelerisque fermentum. " +
                "Dui sapien eget mi proin sed libero enim sed.", 1);

        QM.insertNote("Tortor condimentum lacinia quis vel eros donec ac odio tempor. " +
                "Scelerisque viverra mauris in aliquam sem fringilla ut morbi tincidunt. " +
                "Tempor commodo ullamcorper a lacus vestibulum sed arcu. " +
                "Posuere urna nec tincidunt praesent semper feugiat nibh. " +
                "Aliquam id diam maecenas ultricies mi eget mauris pharetra. " +
                "Vitae auctor eu augue ut lectus arcu bibendum. Molestie nunc non blandit massa enim nec dui nunc. " +
                "Commodo ullamcorper a lacus vestibulum sed. Lectus urna duis convallis convallis tellus id interdum velit. " +
                "Fringilla phasellus faucibus scelerisque eleifend donec. " +
                "Integer feugiat scelerisque varius morbi enim nunc. Est ante in nibh mauris cursus mattis molestie a. " +
                "Sed vulputate mi sit amet mauris commodo.", 3);
        QM.close();

    }
}
