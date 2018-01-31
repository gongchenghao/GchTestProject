package gcg.testproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseFragment;

public class ForthFragment extends BaseFragment implements View.OnClickListener {

    public ForthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
