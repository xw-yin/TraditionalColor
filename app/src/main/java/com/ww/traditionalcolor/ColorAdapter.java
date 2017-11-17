package com.ww.traditionalcolor;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ww.traditionalcolor.weight.RingProgressBar;
import com.ww.traditionalcolor.weight.VerticalProgressBar;

import java.util.List;

/**
 * Created by ww on 2017/10/20.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ContactsViewHolder> {

    private List<ColorData> datas;
    private int layoutId;
    private Context context;
    private onItemClickListener mOnItemClickListener;


    public ColorAdapter(List<ColorData> contacts, int layoutId, Context context) {
        this.datas = contacts;
        this.layoutId = layoutId;
        this.context=context;
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, null);
        return new ContactsViewHolder(view);
    }
    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public void onBindViewHolder(final ContactsViewHolder holder, final int position) {
        final ColorData data = datas.get(position);
        holder.jpName.setText(data.getJpname());
        holder.num.setText(data.getId());
        holder.hex.setText("#"+data.getColor());
        holder.enName.setText(data.getEnname());
        holder.num.setTextColor(Color.parseColor("#"+data.getColor()));
        holder.view.setBackgroundColor(Color.parseColor("#"+data.getColor()));
        holder.r.setProgress(data.getR());
        holder.g.setProgress(data.getG());
        holder.b.setProgress(data.getB());
        holder.c.setProgress(data.getC());
        holder.m.setProgress(data.getM());
        holder.y.setProgress(data.getY());
        holder.k.setProgress(data.getK());
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mOnItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder {
        public TextView num, jpName, enName, hex;
        private RingProgressBar c,m,y,k;
        private VerticalProgressBar r,g,b;
        private View view;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            num =  itemView.findViewById(R.id.num);
            jpName = itemView.findViewById(R.id.jp_name);
            enName =  itemView.findViewById(R.id.en_name);
            hex =  itemView.findViewById(R.id.hex);
            c=itemView.findViewById(R.id.C);
            m=itemView.findViewById(R.id.M);
            y=itemView.findViewById(R.id.Y);
            k=itemView.findViewById(R.id.K);
            r=itemView.findViewById(R.id.R);
            g=itemView.findViewById(R.id.G);
            b=itemView.findViewById(R.id.B);
            view=itemView.findViewById(R.id.view);
        }
    }
    }