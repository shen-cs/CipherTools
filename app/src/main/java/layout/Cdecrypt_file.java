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
 * Created by Shen Chang-Shao on 2016/2/26.
 */
public class Cdecrypt_file extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cdecrypt_file, container, false);
        EditText keyText = (EditText) rootView.findViewById(R.id.key_edit_decrypt_file);
        if(!getActivity().getClass().equals(MainActivity.class)) {
            keyText.setHint(getString(R.string.vhintkey));
        }
        else {
            LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.cdecrypt_file_fragment);
            Spinner spinner = new Spinner(getActivity());
            spinner.setId(R.id.ckey_spinner_decrypt_file);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 16, 0, 0);
            spinner.setLayoutParams(params);
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
