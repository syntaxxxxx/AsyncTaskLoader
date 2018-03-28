package fiqri.com.asynctaskloader;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import fiqri.com.asynctaskloader.adapter.WeatherAdapter;
import fiqri.com.asynctaskloader.clas.AsyncTaskLoader;
import fiqri.com.asynctaskloader.model.WeatherItems;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks
        <ArrayList<WeatherItems>> {

    //deklarasi
    ListView listView;
    WeatherAdapter adapter;
    EditText edtCity;
    Button btnSearch;

    // variable daftar" kota
    static final String EXTRAS_CITY = "EXTRAS_CITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // casting / inisialisasi / hubungkan
        adapter = new WeatherAdapter(this);
        adapter.notifyDataSetChanged();
        listView = findViewById(R.id.list_view);

        // hubungkan listview dengan adapter
        listView.setAdapter(adapter);

        // hubungkan / casting
        edtCity = findViewById(R.id.edit_kota);
        btnSearch = findViewById(R.id.btn_kota);

        // event click button
        btnSearch.setOnClickListener(listener);

        // get nama kota dan ditampilkan
        String city = edtCity.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_CITY, city);

        // inisialisai dari loader / hubugkna loader kedalam onCreate
        getLoaderManager().initLoader(0, bundle, this);
    }

    // menjalankan proses asynctaskloader
    @Override
    public Loader<ArrayList<WeatherItems>> onCreateLoader(int id, Bundle args) {

        String kumpulanKota = "";

        if (args != null) {
            kumpulanKota = args.getString(EXTRAS_CITY);
        }
        return new AsyncTaskLoader(this, kumpulanKota);
    }

    // dieksekusi jika proses loading background selesai
    @Override
    public void onLoadFinished(Loader<ArrayList<WeatherItems>> loader,
                               ArrayList<WeatherItems> data) {

        // dari weatheritems kirim data
        // ke adapter untuk ditampilkan
        adapter.setData(data);
    }

    // dijalankan ulang ketika loader direset
    @Override
    public void onLoaderReset(Loader<ArrayList<WeatherItems>> loader) {

        adapter.setData(null);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String city = edtCity.getText().toString().trim();

            if (TextUtils.isEmpty(city))
                return;

            // bundle = ambil data ketika onclik dijalankan
            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_CITY, city);

            // loader akan direset, lalu onCreateLoader dijalankan
            // fungsinys untuk ambil data ulang yang baru
            // lalu ditampilkannya di loaderfinished
            getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}
