package gcg.testproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.base.BaseFragment;
import gcg.testproject.utils.LogUtils;

public class TabFragment extends BaseFragment {

    @Bind(R.id.tv_tab)
    TextView tvTab;

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            int position = bundle.getInt("position");
            tvTab.setText("我是第"+(position+1)+"页");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
