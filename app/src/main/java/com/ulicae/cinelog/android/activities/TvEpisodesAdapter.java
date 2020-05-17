package com.ulicae.cinelog.android.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ulicae.cinelog.R;
import com.ulicae.cinelog.data.dto.SerieEpisodeDto;
import com.ulicae.cinelog.data.services.reviews.SerieEpisodeService;

import java.util.List;

/**
 * CineLog Copyright 2018 Pierre Rognon
 * <p>
 * <p>
 * This file is part of CineLog.
 * CineLog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * CineLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with CineLog. If not, see <https://www.gnu.org/licenses/>.
 */
public class TvEpisodesAdapter extends ArrayAdapter<SerieEpisodeDto> {

    private SerieEpisodeService serieEpisodeService;

    public TvEpisodesAdapter(Context context, List<SerieEpisodeDto> results, SerieEpisodeService serieEpisodeService) {
        super(context, R.layout.serie_episode_result_item, results);
        this.serieEpisodeService = serieEpisodeService;
    }


    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.serie_episode_result_item, parent, false);
        }

        SerieEpisodeDto item = getItem(position);

        TvEpisodeViewHolder holder = new TvEpisodeViewHolder(convertView);

        if (item != null) {
            setDataInView(item, holder);
            setViewedDataInView(item, holder);
            convertView.setOnClickListener(v -> setViewed(item, holder));
        }

        return convertView;
    }

    private void setDataInView(SerieEpisodeDto item, TvEpisodeViewHolder holder) {
        holder.getEpisodeName().setText(item.getName());
        holder.getEpisodeDate().setText(
                item.getAirDate() != null ?
                        DateFormat.getDateFormat(getContext()).format(item.getAirDate()) : ""
        );
        holder.getSeasonNumber().setText(
                item.getSeasonNumber() != null ? String.valueOf(item.getSeasonNumber() + 1) : ""
        );
        holder.getEpisodeNumber().setText(
                item.getEpisodeNumber() != null ? String.valueOf(item.getEpisodeNumber()) : ""
        );

        holder.getEpisodeViewedDate().setText(
                item.getViewDate() != null ?
                        DateFormat.getDateFormat(getContext()).format(item.getViewDate()) : ""
        );

        if(item.getEpisodeId() != null){
            holder.getEpisodeViewed().setImageResource(R.drawable.round_eye_purple);
        } else {
            holder.getEpisodeViewed().setImageResource(R.drawable.round_eye_grey);
        }
    }

    private void setViewedDataInView(SerieEpisodeDto item, TvEpisodeViewHolder holder) {
        holder.getEpisodeViewedDate().setText(
                item.getViewDate() != null ?
                        DateFormat.getDateFormat(getContext()).format(item.getViewDate()) : ""
        );

        if(item.getEpisodeId() != null){
            holder.getEpisodeViewed().setImageResource(R.drawable.round_eye_purple);
        } else {
            holder.getEpisodeViewed().setImageResource(R.drawable.round_eye_grey);
        }
    }

    private void setViewed(SerieEpisodeDto item, TvEpisodeViewHolder holder) {
        if(item.getEpisodeId() != null){
            Toast.makeText(getContext(), "To remove info, please long click", Toast.LENGTH_LONG).show();
        } else {
            SerieEpisodeDto updatedItem = serieEpisodeService.createOrUpdate(item);
            setViewedDataInView(updatedItem, holder);
        }
    }

}