package nothing.impossible.com.nothing.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nothing.impossible.com.nothing.Adapter.CardsDataAdapter;
import nothing.impossible.com.nothing.Databasehelper;
import nothing.impossible.com.nothing.Model.Que;
import nothing.impossible.com.nothing.R;
import nothing.impossible.com.nothing.cardstack.CardStack;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProvokingThoughtQuesFragment extends Fragment {

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    Databasehelper myDbHelper = new Databasehelper(getContext());
    public ProvokingThoughtQuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_provoking_thought_ques, container, false);
        mCardStack = (CardStack) view.findViewById(R.id.container);

        mCardStack.setContentResource(R.layout.card_content);
//        mCardStack.setStackMargin(20);
        mCardAdapter=new CardsDataAdapter(getContext());
        prepareForInspireStory();
//        mCardAdapter.add("မဂၤလာပါ");
//        mCardAdapter.add("test2");
//        mCardAdapter.add("test3");
//        mCardAdapter.add("test4");
//        mCardAdapter.add("test5");
//        mCardAdapter.add("test6");
//        mCardAdapter.add("test7");

        mCardStack.setAdapter(mCardAdapter);
        mCardStack.setEnableLoop(!mCardStack.isEnableLoop());
//         mCardStack.setEnableRotation(!mCardStack.isEnableRotation());
//        mCardStack.setVisibleCardNum(mCardStack.getVisibleCardNum() + 1);
//        mCardStack.setStackMargin(mCardStack.getStackMargin() + 10);
        mCardStack.reset(true);
        if (mCardStack.getAdapter() != null) {
//            mCardStack.setAdapter(mCardAdapter);


        }

//        btnRestart.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                mCardStack.setAdapter(mCardAdapter);
//                mCardStack.setEnableRotation(!mCardStack.isEnableRotation());
//                mCardStack.reset(true);
//                //mCardStack.setEnableRotation(!mCardStack.isEnableRotation());
//            }
//        });
        return view;
    }
    public void prepareForInspireStory(){
        List<Que> queListDB= myDbHelper.getAllQuestions(getContext());
        for(Que que:queListDB){
            que.setQue((que.getQue()));
            mCardAdapter.add(que.getQue());
        }
        myDbHelper.close();
    }
}
