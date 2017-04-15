package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.shen.ciphertools.MainActivity;
import com.example.shen.ciphertools.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cdecrypt_text extends Fragment {


    public Cdecrypt_text() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cdecrypt_text, container, false);
        EditText keyText = (EditText) rootView.findViewById(R.id.key_edit_decrypt);
        if(!getActivity().getClass().equals(MainActivity.class)) {
            keyText.setHint(getString(R.string.vhintkey));
        }
        else {
            EditText text = (EditText) rootView.findViewById(R.id.text_edit_decrypt);
            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.cdecrypt_text_fragment);
            Spinner spinner = new Spinner(getActivity());
            spinner.setId(R.id.ckey_spinner_decrypt_text);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 40);
            spinner.setLayoutParams(params);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params1.setMargins(0, 0, 0, 10);
            text.setLayoutParams(params1);
            //spinner.setPrompt("Hello World!!!");
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.ckeys, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            layout.removeView(keyText);
            layout.addView(spinner, 1);
        }
        return rootView;
    }

}
