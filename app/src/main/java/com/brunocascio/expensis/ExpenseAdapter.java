package com.brunocascio.expensis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by d3m0n on 02/02/15.
 */
public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Expense> data = Collections.emptyList();
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
        viewHolder.description.setText(e.description);
        viewHolder.amount.setText(e.amount+"");
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }

    class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView amount;

        public ExpenseViewHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.rowDescription);
            amount = (TextView) itemView.findViewById(R.id.rowAmount);
        }
    }
}
