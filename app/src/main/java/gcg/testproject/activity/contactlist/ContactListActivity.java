package gcg.testproject.activity.contactlist;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import gcg.testproject.R;
import gcg.testproject.activity.contactlist.contact.ChineseToEnglish;
import gcg.testproject.activity.contactlist.contact.CompareSort;
import gcg.testproject.activity.contactlist.contact.SideBarView;
import gcg.testproject.activity.contactlist.contact.User;
import gcg.testproject.activity.contactlist.contact.UserAdapter;
import gcg.testproject.base.BaseActivity;
import gcg.testproject.utils.LogUtils;

/**
 * 联系人列表
 *
 * @ClassName:ContactListActivity
 * @PackageName:gcg.testproject.activity.contactlist
 * @Create On 2018/1/15   17:16
 * @Site:http://www.handongkeji.com
 * @author:gongchenghao
 * @Copyrights 2018/1/15 handongkeji All rights reserved.
 */

public class ContactListActivity extends BaseActivity implements SideBarView.LetterSelectListener {

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.sidebarview)
    SideBarView sidebarview;
    @Bind(R.id.tip)
    TextView tip;
    @Bind(R.id.activity_contact_list)
    RelativeLayout activityContactList;

    UserAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        String[] contactsArray = getResources().getStringArray(R.array.data);
        String[] headArray = getResources().getStringArray(R.array.head);

        //模拟添加数据到Arraylist
        int length = contactsArray.length;
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setName(contactsArray[i]);
            String firstSpell = ChineseToEnglish.getFirstSpell(contactsArray[i]);
            String substring = firstSpell.substring(0, 1).toUpperCase();
            if (substring.matches("[A-Z]")) {
                user.setLetter(substring);
            } else {
                user.setLetter("#");
            }
            users.add(user);
        }

        for (int i = 0; i < headArray.length; i++) {
            User user = new User();
            user.setName(headArray[i]);
            user.setLetter("@");
            users.add(user);
        }

        //排序
        Collections.sort(users, new CompareSort());

        //设置数据
        mAdapter = new UserAdapter(this);
        mAdapter.setData(users);
        listview.setAdapter(mAdapter);

        //设置回调
        sidebarview.setOnLetterSelectListen(this);

    }


    @Override
    public void onLetterSelected(String letter) {
        setListviewPosition(letter);
        tip.setText(letter);
        tip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLetterChanged(String letter) {
        setListviewPosition(letter);
        tip.setText(letter);
    }

    @Override
    public void onLetterReleased(String letter) {
        tip.setVisibility(View.GONE);
    }

    private void setListviewPosition(String letter) {
        if (letter.equals("↑")){
            listview.setSelection(0);
        }else if (letter.equals("☆")){
            setListviewPosition("A");
        }else {
            int firstLetterPosition = mAdapter.getFirstLetterPosition(letter);
            if (firstLetterPosition != -1) {
                listview.setSelection(firstLetterPosition);
            }
        }

    }
}
