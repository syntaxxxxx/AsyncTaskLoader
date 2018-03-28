package fiqri.com.asynctaskloader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fiqri.com.asynctaskloader.R;
import fiqri.com.asynctaskloader.model.WeatherItems;

//TODO 2 Impelement Methods
public class WeatherAdapter extends BaseAdapter {

    private ArrayList<WeatherItems> data = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<WeatherItems> items) {
        data = items;
        notifyDataSetChanged();
    }

    public void addItem(final WeatherItems item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    @Override
    public WeatherItems getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.items_weather, null);
            holder.namaKota = convertView.findViewById(R.id.text_kota);
            holder.temp = convertView.findViewById(R.id.text_temp);
            holder.desk = convertView.findViewById(R.id.text_desk);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.namaKota.setText(data.get(position).getNama());
        holder.temp.setText(data.get(position).getTemp());
        holder.desk.setText(data.get(position).getDesk());

        return convertView;
    }

    private class ViewHolder {
        TextView namaKota, temp, desk;
    }
}
