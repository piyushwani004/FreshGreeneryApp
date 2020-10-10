package com.piyush004.projectfirst.owner.messdetails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.piyush004.projectfirst.Dashboard.OwnerDashboard;
import com.piyush004.projectfirst.R;

public class MessDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton backButton;
    private EditText editTextName, editTextAddress, editTextMobile, editTextEmail, editTextCity, editTextClosedDays;
    private TextView textViewHeader, textViewName, textViewAddress, textViewMobile, textViewEmail, textViewCity, textViewClosedDays;
    private Button buttonSave;
    private static int SELECT_PHOTO = 1;
    private ImageView imageView;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_details);

        toolbar = findViewById(R.id.toolbarMessDetails);
        backButton = findViewById(R.id.messdetailsBackBtn);
        textViewHeader = findViewById(R.id.messDetailsHeader);
        imageView = findViewById(R.id.messPhoto);

        textViewName = findViewById(R.id.text_view_mess_name);
        textViewAddress = findViewById(R.id.text_view_address);
        textViewMobile = findViewById(R.id.text_view_mobile);
        textViewEmail = findViewById(R.id.text_view_email);
        textViewCity = findViewById(R.id.text_view_city);
        textViewClosedDays = findViewById(R.id.text_view_closed_days);

        editTextName = findViewById(R.id.edit_text_name);
        editTextAddress = findViewById(R.id.edit_text_address);
        editTextMobile = findViewById(R.id.edit_text_mobile);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextCity = findViewById(R.id.edit_text_city);
        editTextClosedDays = findViewById(R.id.edit_text_closed_days);

        buttonSave = findViewById(R.id.button_save);

    }

    public void onClickMessDetailsBackBtn(View view) {
        Intent intent = new Intent(MessDetailsActivity.this, OwnerDashboard.class);
        startActivity(intent);
    }

    public void onClickImageViewMessDetails(View view) {
        Toast.makeText(this, "Select Image", Toast.LENGTH_LONG).show();
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    public void onClickSaveEvent(View view) {

    }
}