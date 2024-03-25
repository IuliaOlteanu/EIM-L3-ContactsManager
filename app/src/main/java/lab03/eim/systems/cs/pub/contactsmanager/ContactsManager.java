package lab03.eim.systems.cs.pub.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManager extends AppCompatActivity {
    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText jobTitleEditText;
    EditText companyEditText;
    EditText websiteEditText;
    EditText imEditText;

    Button saveButton;
    Button cancelButton;
    Button showHideButton;

    LinearLayout additionalFieldsContainer;

    private ButtonListener btn = new ButtonListener();
    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.save) {
                // save contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (nameEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, nameEditText.getText().toString());
                }
                if (phoneEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneEditText.getText().toString());
                }
                if (emailEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, emailEditText.getText().toString());
                }
                if (addressEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, addressEditText.getText().toString());
                }
                if (jobTitleEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitleEditText.getText().toString());
                }
                if (companyEditText != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, companyEditText.getText().toString());
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (websiteEditText != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, websiteEditText.getText().toString());
                    contactData.add(websiteRow);
                }
                if (imEditText != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, imEditText.getText().toString());
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            } else if (view.getId() == R.id.cancel) {
                finish();
            } else if (view.getId() == R.id.showAndHide) {
                if (additionalFieldsContainer.getVisibility() == View.VISIBLE) {
                    additionalFieldsContainer.setVisibility(View.INVISIBLE);
                    showHideButton.setText("Show Additional Fields");
                } else {
                    additionalFieldsContainer.setVisibility(View.VISIBLE);
                    showHideButton.setText("Hide Additional Fields");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab03.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

        nameEditText = (EditText) findViewById(R.id.name);
        phoneEditText = (EditText) findViewById(R.id.phone);
        emailEditText = (EditText) findViewById(R.id.email);
        addressEditText = (EditText) findViewById(R.id.address);
        jobTitleEditText = (EditText) findViewById(R.id.jobTitle);
        companyEditText = (EditText) findViewById(R.id.company);
        websiteEditText = (EditText) findViewById(R.id.website);
        imEditText = (EditText) findViewById(R.id.im);

        saveButton = (Button) findViewById(R.id.save);
        cancelButton = (Button) findViewById(R.id.cancel);
        showHideButton = (Button) findViewById(R.id.showAndHide);
        additionalFieldsContainer = (LinearLayout) findViewById(R.id.additionalFields);

        saveButton.setOnClickListener(btn);
        cancelButton.setOnClickListener(btn);
        showHideButton.setOnClickListener(btn);
    }
}