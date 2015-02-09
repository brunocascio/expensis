package com.brunocascio.expensis.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brunocascio.expensis.Models.Expense;
import com.brunocascio.expensis.R;

import java.util.List;

/**
 * Created by d3m0n on 02/02/15.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    List<Expense> data;

    public ExpenseAdapter(Context context, List<Expense> data){
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.data = data;
    }

    public void removeExpense(int pos) {
        data.remove(pos);
        this.notifyItemRemoved(pos);
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = layoutInflater.inflate(R.layout.expense_row, viewGroup, false);

        ExpenseViewHolder evh = new ExpenseViewHolder(view, context);

        return evh;
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, int i) {
        Expense e = data.get(i);
        viewHolder.setItem(e);
        viewHolder.description.setText(e.getDescription());
        viewHolder.amount.setText("$"+e.getAmount()+"");
        viewHolder.date.setText(e.getFullDateWithOutYear());

        // Here you apply the animation when the view is bound
        setAnimation(viewHolder.container, android.R.anim.slide_in_left);
    }

    private void setAnimation(LinearLayout viewToAnimate, int animationType){
        // If the bound view wasn't previously displayed on screen, it's animated
        viewToAnimate.startAnimation(
                AnimationUtils.loadAnimation(context, animationType)
        );
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    public Expense getExpense(int position) {
        return this.data.get(position);
    }


    class ExpenseViewHolder extends RecyclerView.ViewHolder{

        private TextView description;
        private TextView amount;
        private TextView date;
        private LinearLayout container;
        private Context context;
        private Expense e;

        public ExpenseViewHolder(View itemView, Context context) {
            super(itemView);

            this.container = (LinearLayout) itemView.findViewById(R.id.card_container);
            this.context = context;
            this.description = (TextView) itemView.findViewById(R.id.rowDescription);
            this.amount = (TextView) itemView.findViewById(R.id.rowAmount);
            this.date = (TextView) itemView.findViewById(R.id.rowDate);
        }

        public void setItem(Expense e) {
            this.e = e;
        }

        public Expense getItem() {
            return this.e;
        }
    }
}
