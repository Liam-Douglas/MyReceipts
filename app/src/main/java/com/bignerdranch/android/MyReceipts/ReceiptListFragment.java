package com.bignerdranch.android.MyReceipts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

public class ReceiptListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_receipt_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Receipt receipt = new Receipt();
                ReceiptLab.get(getActivity()).addReceipt(receipt);
                Intent intent = ReceiptActivity
                        .newIntent(getActivity(), receipt.getId());
                startActivity(intent);
                return true;
            case R.id.help_button:
                Intent helpIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Receipt"));
                startActivity(helpIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        ReceiptLab receiptLab = ReceiptLab.get(getActivity());
        List<Receipt> receipts = receiptLab.getReceipts();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(receipts);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(receipts);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mShopnameTextView;
        private TextView mCommentTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Receipt mReceipt;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_receipt, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.receipt_title);
            mShopnameTextView = (TextView) itemView.findViewById(R.id.receipt_shop_name);
            mCommentTextView = (TextView) itemView.findViewById(R.id.receipt_comment);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.receipt_solved);
        }

        public void bind(Receipt receipt) {
            mReceipt = receipt;
            mTitleTextView.setText(mReceipt.getTitle());
            mShopnameTextView.setText(mReceipt.getShopname());
            //mCommentTextView.setText(mReceipt.getComment());
            mDateTextView.setText(mReceipt.getDate().toString());
            mSolvedImageView.setVisibility(receipt.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = ReceiptActivity.newIntent(getActivity(), mReceipt.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Receipt> mReceipts;

        public CrimeAdapter(List<Receipt> receipts) {
            mReceipts = receipts;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Receipt receipt = mReceipts.get(position);
            holder.bind(receipt);
        }

        @Override
        public int getItemCount() {
            return mReceipts.size();
        }

        public void setCrimes(List<Receipt> receipts) {
            mReceipts = receipts;
        }
    }
}
