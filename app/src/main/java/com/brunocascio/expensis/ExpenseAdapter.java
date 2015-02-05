package com.brunocascio.expensis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by d3m0n on 02/02/15.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Expense> data;
    private Context context;

    public ExpenseAdapter(Context context, List<Expense> data){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.expense_row, viewGroup, false);

        ExpenseViewHolder evh = new ExpenseViewHolder(view);

        return evh;
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, int i) {
        Expense e = data.get(i);
        viewHolder.description.setText(e.getDescription());
        viewHolder.amount.setText("$"+e.getAmount()+"");
        viewHolder.date.setText(e.getFullDateWithOutYear());

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.container);
    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        viewToAnimate.startAnimation(
                AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        );
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView amount;
        TextView date;
        LinearLayout container;

        public ExpenseViewHolder(View itemView) {
            super(itemView);

            this.container= (LinearLayout) itemView.findViewById(R.id.card_container);

            description = (TextView) itemView.findViewById(R.id.rowDescription);
            amount = (TextView) itemView.findViewById(R.id.rowAmount);
            date = (TextView) itemView.findViewById(R.id.rowDate);
        }
    }
}
