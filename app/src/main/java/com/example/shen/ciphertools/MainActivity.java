package com.example.shen.ciphertools;


import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


import layout.SampleFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Encrypt Text");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_layout_main);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setElevation(0);
        //actionBar.setIcon(R.drawable.actionbar_logo);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SampleFragmentPagerAdapter pagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(pagerAdapter);
        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(pagerAdapter.getTabView(i));
            if (i > 0) {
                switchIcon(tabLayout.getTabAt(i), false);
            }
        }

        //input.setInputType(InputType.TYPE_CLASS_TEXT);
        // Iterate over all tabs and set the custom view
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switchTitle(pos);
                switchIcon(tab, true);
                viewPager.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //switchTitle(tab.getPosition());
                //tab.setCustomView(pagerAdapter.getTabView(tab.getPosition()));
                //viewPager.setCurrentItem(tab.getPosition());
                switchIcon(tab, false);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab.setCustomView(pagerAdapter.getTabView(3 - tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about_cipher) {
            AboutCaesar dialog = new AboutCaesar();
            dialog.show(getSupportFragmentManager(), "dialogCaesar");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //encrypt and decrypt handling
    int[] idsMessage = {R.id.text_edit_encrypt, R.id.text_edit_decrypt, R.id.encrypt_file_text, R.id.decrypt_file_text};
    int[] idsResult = {R.id.encrypt_text_view, R.id.decrypt_text_view, R.id.encrypt_file_text_view, R.id.decrypt_file_text_view};
    int[] saveButtons = {R.id.encrypt_save_button, R.id.decrypt_save_button};
    int[] sendButtons = {R.id.encrypt_send_button, R.id.decrypt_send_button};
    int[] spinKeys ={R.id.ckey_spinner_encrypt_text, R.id.ckey_spinner_decrypt_text, R.id.ckey_spinner_encrypt_file, R.id.ckey_spinner_decrypt_file};


    public void showEncrypt(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        String text = getMessage(pos);
        int key = getKey(pos);
        TextView result = (TextView) findViewById(idsResult[pos]);
        if (key != -1) {
            CaesarCipher cc = makeCipher(key);
            String encrypted = cc.encrypt(text);
            result.setText(encrypted);
            if (pos > 1) {
                Button saver = (Button) findViewById(saveButtons[pos - 2]);
                saver.setVisibility(View.VISIBLE);
                Button sender = (Button) findViewById(sendButtons[pos - 2]);
                sender.setVisibility(View.VISIBLE);
            }
        }

    }

    public void showDecrypt(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        String text = getMessage(pos);
        int key = getKey(pos);
        TextView decrypt = (TextView) findViewById(idsResult[pos]);
        if (key != -1) {
            CaesarCipher cb = makeCipher(key);
            String decrypted = cb.decrypt(text);
            decrypt.setText(decrypted);
            if (pos > 1) {
                Button saver = (Button) findViewById(saveButtons[pos - 2]);
                saver.setVisibility(View.VISIBLE);
                Button sender = (Button) findViewById(sendButtons[pos - 2]);
                sender.setVisibility(View.VISIBLE);
            }
        }
    }

    private CaesarCipher makeCipher(int key) {
        return new CaesarCipher(key);
    }

    private String getMessage(int idx) {

        TextView Emessage = (TextView) findViewById(idsMessage[idx]);
        String text = Emessage.getText().toString();
        if (text.equals("")) {
            callToast("Have you enter the message?");
            return "";
        }
        return Emessage.getText().toString();
    }

    private int getKey(int idx) {
        //TextView Ekey = (TextView) findViewById(keys[idx]);
        Spinner Ekey = (Spinner) findViewById(spinKeys[idx]);
        if (Ekey.getSelectedItem().toString().equals("")) {
            callToast("Have you enter the key?");
            return -1;
        }
        try {
            int key = Integer.parseInt(Ekey.getSelectedItem().toString());
            if (!(0 <= key && key <= 25)) {
                callToast(("Please enter a key number between 0 and 25"));
                return -1;
            }
            return key;
        } catch (Exception e) {
            callToast("Please enter a key number between 0 and 25");
            return -1;
        }

    }

    public void clear(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        int[] idsFileName = {R.id.encrypt_file_name, R.id.decrypt_file_name};
        int[] hints = {R.string.file_hint_encrypt, R.string.file_hint_decrypt};
        TextView Emessage = (TextView) findViewById(idsMessage[pos]);
        Emessage.setText("");
        Spinner key = (Spinner) findViewById(spinKeys[pos]);
        key.setSelection(0);
        TextView result = (TextView) findViewById(idsResult[pos]);
        result.setText("");
        if (pos > 1) {
            TextView fileName = (TextView) findViewById(idsFileName[pos - 2]);
            fileName.setText(getResources().getString(hints[pos - 2]));
//            LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.CENTER_HORIZONTAL;
//            fileName.setLayoutParams(params);
            Button saver = (Button) findViewById(saveButtons[pos - 2]);
            saver.setVisibility(View.INVISIBLE);
            Button sender = (Button) findViewById(sendButtons[pos - 2]);
            sender.setVisibility(View.INVISIBLE);
        }
    }

    public void sendMessage(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        int[] titles = {R.string.chooser_title_encrypt_text, R.string.chooser_title_decrypt_text};
        TextView text = (TextView) findViewById(idsResult[pos]);
        Spinner key = (Spinner) findViewById(spinKeys[pos]);
        String message = text.getText().toString() + "\n" + "key: " + key.getSelectedItem().toString();
        String title = getResources().getString(titles[pos % 2]);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);
        Intent chooser = Intent.createChooser(intent, title);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    public void copy(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        int[] toasts = {R.string.encrypt_text_copy_toast, R.string.decrypt_text_copy_toast};
        TextView text = (TextView) findViewById(idsResult[pos]);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("result", text.getText().toString());
        clipboard.setPrimaryClip(clip);
        callToast(getResources().getString(toasts[pos % 2]));
    }

    public void paste(View view) {
        int pos = tabLayout.getSelectedTabPosition();
        int[] id2paste = {R.id.text_edit_encrypt, R.id.text_edit_decrypt};
        if (pos > 1) {
            callToast(getResources().getString(R.string.paste_error));
            return;
        }
        EditText Emessage = (EditText) findViewById(id2paste[pos]);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String text = "";
        if (clipboard.hasPrimaryClip()) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            text = item.getText().toString();
            Emessage.setText(text);
            callToast(getResources().getString(R.string.paste_message));
        }

    }


    private void callToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0 , -40);
        toast.show();
    }

    private void switchTitle(int pos) {
        String[] titles = {"Encrypt Text", "Decrypt Text", "Encrypt File", "Decrypt File"};
        TextView label = (TextView) findViewById(R.id.title_actionbar);
        label.setText(titles[pos]);
    }

    private void switchIcon(TabLayout.Tab tab, boolean selected) {
        int[] images = {R.mipmap.encrypt_text_image, R.mipmap.decrypt_text, R.mipmap.encrypt_file_image, R.mipmap.decrypt_file_image};
        int pos = tab.getPosition();
        View v = tab.getCustomView();
        ImageView img = (ImageView) v.findViewById(R.id.icon_tab);
        img.setImageResource(images[pos]);
        if (selected) {
            int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabSelectedIconColor);
            img.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        } else {
            int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabUnselectedIconColor);
            img.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        }
        tab.setCustomView(v);
    }

    //==================File handling==========================================
    final int FILE_CHOOSER_REQUEST = 1;
    String filePath = "";
    String fileName = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        int[] idsText = {R.id.encrypt_file_text, R.id.decrypt_file_text};
        int[] idsFileName = {R.id.encrypt_file_name, R.id.decrypt_file_name};
        int pos = tabLayout.getSelectedTabPosition();
        // Check which request we're responding to
        if (requestCode == FILE_CHOOSER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                TextView name = (TextView) findViewById(idsFileName[pos - 2]);
//                LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//                params.gravity = Gravity.CENTER_HORIZONTAL;
//                name.setLayoutParams(params);
                File f = new File(uri.getPath());
                filePath = f.getAbsolutePath();
                fileName = f.getName();
                name.setText("File name: " + f.getName());
                TextView path_text = (TextView) findViewById(idsText[pos - 2]);
                try {
                    path_text.setText(readTextFromUri(uri));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void showFileChooser(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/plain");     //all files
        //intent.setType("text/xml");   //XML file only
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_CHOOSER_REQUEST);
        } catch (ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        StringBuilder text = new StringBuilder();
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                inputStream));
        String line;
        while ((line = br.readLine()) != null) {
            text.append(line);
            text.append('\n');
        }
        inputStream.close();
        br.close();
        return text.toString();

    }

    public void saveFile(View view) {
        File file = new File(filePath);
        // check sd card
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            callToast("SD card undetected.");
            return;
        }

        int pos = tabLayout.getSelectedTabPosition();
        String[] prefix = {"encrypted-", "decrypted-"};
        int[] pops = {R.string.encrypt_file_save_toast, R.string.decrypt_file_save_toast};
        String fName = prefix[pos - 2] + fileName;
        String fPath = "";
        try {
            File sd = Environment.getExternalStorageDirectory();
            fPath = sd.getAbsolutePath() + "/" + getResources().getString(R.string.app_name);
            //fPath = filePath;
            File dir = new File(fPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file = new File(dir, fName);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("MainAcitivty", "sd failed");
        }
        try {
            TextView result = (TextView) findViewById(idsResult[pos]);
            // get message and key
            int key = getKey(pos);
            String message = "Key: " + key + "\n" + result.getText();
            //save file
            FileOutputStream out = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(message);
            writer.flush();
            writer.close();
            out.close();
            Toast.makeText(getApplicationContext(), getResources().getString(pops[pos - 2]) + "\nFile path: " + fPath, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//==================================================================================
}
