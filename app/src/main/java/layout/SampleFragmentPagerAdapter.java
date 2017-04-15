package layout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shen.ciphertools.MainActivity;
import com.example.shen.ciphertools.R;

/**
 * Created by Shen Chang-Shao on 2016/2/25.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 4;
    private int images[] = {R.mipmap.encrypt_text_image, R.mipmap.decrypt_text, R.mipmap.encrypt_file_image, R.mipmap.decrypt_file_image};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //return PageFragment.newInstance(position + 1);
        switch (position) {
            case 0:
                return new Cencrypt_text();
            case 1:
                return new Cdecrypt_text();
            case 2:
                return new Cencrypt_file();
            case 3:
                return new Cdecrypt_file();
            default:
                return null;
        }
    }


    //   @Override
//    public CharSequence getPageTitle(int position) {
    // Generate title based on item position
    // Generate title based on item position
    // return tabTitles[position];

    // getDrawable(int i) is deprecated, use getDrawable(int i, Theme theme) for min SDK >=21
    // or ContextCompat.getDrawable(Context context, int id) if you want support for older versions.
    // Drawable image = context.getResources().getDrawable(iconIds[position], context.getTheme());
    // Drawable image = context.getResources().getDrawable(imageResId[position]);

    /*Drawable image = ContextCompat.getDrawable(context, images[position]);
    image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
    SpannableString sb = new SpannableString(" ");
    ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
    sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return sb;*/
    //       return tabTitles[position];
    //  }
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.selected_tab, null);
        ImageView img = (ImageView) v.findViewById(R.id.icon_tab);
        img.setImageResource(images[position]);
        return v;
    }

}
