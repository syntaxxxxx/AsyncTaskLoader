package fiqri.com.asynctaskloader.clas;

import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import fiqri.com.asynctaskloader.model.WeatherItems;

//TODO Implement Methods
public class AsyncTaskLoader extends android.content.
        AsyncTaskLoader<ArrayList<WeatherItems>> {

    private ArrayList<WeatherItems> mData;
    private boolean result = false;
    private String kumpulanKota;

    public AsyncTaskLoader(Context context, String kumpulanKota) {
        super(context);

        onContentChanged();
        this.kumpulanKota = kumpulanKota;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            // ambil data
            // jika data belum ada
            forceLoad();

        else if (result)
            deliverResult(mData);
    }


    // nampilin data yg sudah ada
    // dijalankan juga
    // jika terjadi reset pada loader
    @Override
    public void deliverResult(ArrayList<WeatherItems> data) {
        mData = data;
        result = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();

        // ketika proses reset
        // cek result
        // bila hasil datanya ada
        // onReleaseResource akan dijalankan
        if (result) {
            onReleaseResource(mData);
            mData = null;
            result = false;
        }
    }

    // variable buat api_key
    private static final String API_KEY = "610f6dfb068b217f3396b6241ed45534";

    // proses pengambilan data
    @Override
    public ArrayList<WeatherItems> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<WeatherItems> weatherItemses = new ArrayList<>();

        String url = "http://api.openweathermap.org/data/2.5/group?id="
                + kumpulanKota + "&units=metric&appid=" + API_KEY;

        // pada dasarnya thread yg digunakan sudah asynchronous
        // tapi kita menggunakan method setUseSynchronousMode
        // dengan nilai true, untuk menjadi synchronous
        // karena ingin mengembalikan nilai balik
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems(weather);
                        weatherItemses.add(weatherItems);
                    }

                    // jika saat parsing error
                    // error handling
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // jika response gagal
            }
        });
        return weatherItemses;
    }

    private void onReleaseResource(ArrayList<WeatherItems> data) {
    }
}









